package com.mtrxxp.backend.repository;

import com.mtrxxp.backend.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
    Optional<OrderModel> findByOrderId(String orderId);
}
