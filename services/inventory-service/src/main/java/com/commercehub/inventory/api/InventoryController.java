package com.commercehub.inventory.api;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
  @GetMapping("/stock/{sku}") public Map<String,Object> stock(@PathVariable String sku){ return Map.of("sku",sku,"onHand",100,"reserved",5); }
  @PostMapping("/reservations") public Map<String,String> reserve(){ return Map.of("status","RESERVED"); }
}
