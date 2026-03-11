package com.commercehub.cart.api;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
  @GetMapping("/carts/me") public Map<String,Object> me(){ return Map.of("status","ACTIVE","items",List.of()); }
  @PostMapping("/carts/me/items") public Map<String,String> add(){ return Map.of("status","item-added"); }
  @PatchMapping("/carts/me/items/{itemId}") public Map<String,String> patch(@PathVariable Long itemId){ return Map.of("status","item-updated","itemId",itemId.toString()); }
  @DeleteMapping("/carts/me/items/{itemId}") public void del(@PathVariable Long itemId){}
}
