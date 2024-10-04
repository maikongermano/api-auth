package com.loja.auth.api_auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loja.auth.api_auth.model.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	Optional<Order> findById(String orderId);
}
