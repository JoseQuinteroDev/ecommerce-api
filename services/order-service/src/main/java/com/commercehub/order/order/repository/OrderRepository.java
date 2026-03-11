package com.commercehub.order.order.repository;

import com.commercehub.order.order.entity.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
  List<OrderEntity> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
