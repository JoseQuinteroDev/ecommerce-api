package com.commercehub.order.api;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
  @PostMapping("/orders/checkout") public Map<String,String> checkout(){ return Map.of("orderId",java.util.UUID.randomUUID().toString(),"status","PENDING"); }
  @GetMapping("/orders/me") public List<Map<String,String>> me(){ return List.of(Map.of("orderNumber","ORD-0001","status","PAID")); }
  @GetMapping("/orders/{orderId}") public Map<String,String> byId(@PathVariable String orderId){ return Map.of("orderId",orderId,"status","CONFIRMED"); }
  @PatchMapping("/orders/{orderId}/cancel") public Map<String,String> cancel(@PathVariable String orderId){ return Map.of("orderId",orderId,"status","CANCELLED"); }
}
