package com.commercehub.order.order.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private OrderEntity order;
  private String sku;
  @Column(name = "product_name_snapshot")
  private String productNameSnapshot;
  @Column(name = "unit_price")
  private BigDecimal unitPrice;
  private Integer quantity;
  @Column(name = "line_total")
  private BigDecimal lineTotal;

  public void setOrder(OrderEntity order) { this.order = order; }
  public void setSku(String sku) { this.sku = sku; }
  public void setProductNameSnapshot(String productNameSnapshot) { this.productNameSnapshot = productNameSnapshot; }
  public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }
  public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
}
