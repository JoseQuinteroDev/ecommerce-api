package com.commercehub.catalog.api;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
  @GetMapping("/products") public List<Map<String,Object>> products(){ return List.of(Map.of("id",1,"name","Producto demo","sku","SKU-001")); }
  @GetMapping("/products/{id}") public Map<String,Object> product(@PathVariable Long id){ return Map.of("id",id,"name","Producto "+id); }
  @GetMapping("/categories") public List<String> categories(){ return List.of("Electrónica","Hogar"); }
  @PostMapping("/products") public Map<String,String> create(){ return Map.of("status","created"); }
  @PatchMapping("/products/{id}") public Map<String,String> patch(@PathVariable Long id){ return Map.of("status","updated","id",id.toString()); }
}
