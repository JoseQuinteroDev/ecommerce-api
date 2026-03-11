package com.commercehub.order.order.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {
  @Id
  private String id = UUID.randomUUID().toString();
  @Column(name = "order_number", nullable = false, unique = true)
  private String orderNumber;
  @Column(name = "customer_id", nullable = false)
  private Long customerId;
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
  private BigDecimal subtotal;
  @Column(name = "discount_total")
  private BigDecimal discountTotal;
  @Column(name = "shipping_total")
  private BigDecimal shippingTotal;
  @Column(name = "tax_total")
  private BigDecimal taxTotal;
  @Column(name = "grand_total")
  private BigDecimal grandTotal;
  private String currency;
  @Column(name = "created_at")
  private Instant createdAt = Instant.now();

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItemEntity> items = new ArrayList<>();

  public String getId() { return id; }
  public String getOrderNumber() { return orderNumber; }
  public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
  public Long getCustomerId() { return customerId; }
  public void setCustomerId(Long customerId) { this.customerId = customerId; }
  public OrderStatus getStatus() { return status; }
  public void setStatus(OrderStatus status) { this.status = status; }
  public BigDecimal getSubtotal() { return subtotal; }
  public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
  public BigDecimal getDiscountTotal() { return discountTotal; }
  public void setDiscountTotal(BigDecimal discountTotal) { this.discountTotal = discountTotal; }
  public BigDecimal getShippingTotal() { return shippingTotal; }
  public void setShippingTotal(BigDecimal shippingTotal) { this.shippingTotal = shippingTotal; }
  public BigDecimal getTaxTotal() { return taxTotal; }
  public void setTaxTotal(BigDecimal taxTotal) { this.taxTotal = taxTotal; }
  public BigDecimal getGrandTotal() { return grandTotal; }
  public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }
  public List<OrderItemEntity> getItems() { return items; }
}
