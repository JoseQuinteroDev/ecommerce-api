package com.commercehub.order.api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order/legacy")
public class OrderController {
  @GetMapping
  public Map<String, String> info() {
    return Map.of("message", "Legacy controller disabled. Use /order/orders endpoints.");
  }
}
