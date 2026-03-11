package com.commercehub.customer.api;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
  @GetMapping("/profile/me") public Map<String,String> me(){ return Map.of("firstName","Demo","lastName","Customer"); }
}
