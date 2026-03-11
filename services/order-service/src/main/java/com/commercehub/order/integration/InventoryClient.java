package com.commercehub.order.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "${clients.inventory.url:http://localhost:8084}")
public interface InventoryClient {
  @PostMapping("/inventory/reservations")
  void reserve(@RequestBody Object payload);
}
