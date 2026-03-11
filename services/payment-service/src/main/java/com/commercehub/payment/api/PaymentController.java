package com.commercehub.payment.api;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
  @PostMapping("/payments") public Map<String,String> create(){ return Map.of("paymentId",java.util.UUID.randomUUID().toString(),"status","INITIATED"); }
  @PostMapping("/payments/{paymentId}/confirm") public Map<String,String> confirm(@PathVariable String paymentId){ return Map.of("paymentId",paymentId,"status","CAPTURED"); }
  @PostMapping("/payments/{paymentId}/webhook") public Map<String,String> webhook(@PathVariable String paymentId){ return Map.of("paymentId",paymentId,"event","mock-webhook-received"); }
}
