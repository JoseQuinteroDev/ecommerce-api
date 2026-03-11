package com.commercehub.order.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${clients.payment.url:http://localhost:8087}")
public interface PaymentClient {
  @PostMapping("/payment/payments")
  void createPayment(@RequestBody Object payload);
}
