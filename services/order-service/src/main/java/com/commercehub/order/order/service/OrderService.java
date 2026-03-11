package com.commercehub.order.order.service;

import com.commercehub.order.integration.InventoryClient;
import com.commercehub.order.integration.PaymentClient;
import com.commercehub.order.order.dto.OrderDtos.*;
import com.commercehub.order.order.entity.OrderEntity;
import com.commercehub.order.order.entity.OrderItemEntity;
import com.commercehub.order.order.entity.OrderStatus;
import com.commercehub.order.order.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final InventoryClient inventoryClient;
  private final PaymentClient paymentClient;

  public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient, PaymentClient paymentClient) {
    this.orderRepository = orderRepository;
    this.inventoryClient = inventoryClient;
    this.paymentClient = paymentClient;
  }

  @Transactional
  public CheckoutResponse checkout(CheckoutRequest request) {
    if (request.items() == null || request.items().isEmpty()) throw new IllegalArgumentException("El checkout requiere ítems");
    OrderEntity order = new OrderEntity();
    order.setOrderNumber("ORD-" + System.currentTimeMillis());
    order.setCustomerId(request.customerId());
    order.setStatus(OrderStatus.PENDING);
    order.setCurrency(request.currency() == null ? "USD" : request.currency());
    BigDecimal subtotal = BigDecimal.ZERO;
    for (CheckoutItem item : request.items()) {
      OrderItemEntity orderItem = new OrderItemEntity();
      orderItem.setOrder(order);
      orderItem.setSku(item.sku());
      orderItem.setProductNameSnapshot(item.productNameSnapshot());
      orderItem.setUnitPrice(item.unitPrice());
      orderItem.setQuantity(item.quantity());
      BigDecimal line = item.unitPrice().multiply(BigDecimal.valueOf(item.quantity()));
      orderItem.setLineTotal(line);
      order.getItems().add(orderItem);
      subtotal = subtotal.add(line);
    }
    order.setSubtotal(subtotal);
    order.setDiscountTotal(BigDecimal.ZERO);
    order.setShippingTotal(BigDecimal.ZERO);
    order.setTaxTotal(BigDecimal.ZERO);
    order.setGrandTotal(subtotal);

    OrderEntity saved = orderRepository.save(order);
    inventoryClient.reserve(Map.of("orderId", saved.getId(), "items", request.items()));
    paymentClient.createPayment(Map.of("orderId", saved.getId(), "amount", saved.getGrandTotal(), "currency", saved.getCurrency()));
    saved.setStatus(OrderStatus.CONFIRMED);
    return new CheckoutResponse(saved.getId(), saved.getOrderNumber(), saved.getStatus().name(), saved.getGrandTotal());
  }

  @Transactional(readOnly = true)
  public List<OrderView> myOrders(Long customerId) {
    return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
        .map(it -> new OrderView(it.getId(), it.getOrderNumber(), it.getStatus().name(), it.getGrandTotal(), it.getCurrency()))
        .toList();
  }

  @Transactional(readOnly = true)
  public OrderView getById(String id) {
    OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
    return new OrderView(order.getId(), order.getOrderNumber(), order.getStatus().name(), order.getGrandTotal(), order.getCurrency());
  }

  @Transactional
  public OrderView cancel(String id) {
    OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
    if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
      throw new IllegalArgumentException("No se puede cancelar un pedido ya enviado/entregado");
    }
    order.setStatus(OrderStatus.CANCELLED);
    return new OrderView(order.getId(), order.getOrderNumber(), order.getStatus().name(), order.getGrandTotal(), order.getCurrency());
  }
}
