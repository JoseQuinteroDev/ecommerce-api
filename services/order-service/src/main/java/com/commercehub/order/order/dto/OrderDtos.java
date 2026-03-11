package com.commercehub.order.order.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDtos {
  public record CheckoutRequest(Long customerId, List<CheckoutItem> items, String currency) {}
  public record CheckoutItem(String sku, String productNameSnapshot, BigDecimal unitPrice, Integer quantity) {}
  public record CheckoutResponse(String orderId, String orderNumber, String status, BigDecimal grandTotal) {}
  public record OrderView(String orderId, String orderNumber, String status, BigDecimal grandTotal, String currency) {}
}
