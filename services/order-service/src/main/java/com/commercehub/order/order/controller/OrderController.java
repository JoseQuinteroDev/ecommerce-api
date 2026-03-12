package com.commercehub.order.order.controller;

import com.commercehub.order.order.dto.OrderDtos.*;
import com.commercehub.order.order.service.OrderService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) { this.orderService = orderService; }

  @PostMapping("/checkout")
  public CheckoutResponse checkout(@RequestBody CheckoutRequest request) {
    return orderService.checkout(request);
  }

  @GetMapping("/me")
  public List<OrderView> me(@RequestParam Long customerId) {
    return orderService.myOrders(customerId);
  }

  @GetMapping("/{orderId}")
  public OrderView byId(@PathVariable String orderId) {
    return orderService.getById(orderId);
  }

  @PatchMapping("/{orderId}/cancel")
  public OrderView cancel(@PathVariable String orderId) {
    return orderService.cancel(orderId);
  }
}
