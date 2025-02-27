package com.loja.auth.api_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loja.auth.api_auth.model.dto.OrderDTO;
import com.loja.auth.api_auth.model.enums.OrderStatus;
import com.loja.auth.api_auth.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderDTO);
    }
    
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus newStatus) {
        orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/by-company/{companyId}")
    public ResponseEntity<Page<OrderDTO>> getOrdersByCompanyId(@PathVariable Long companyId, Pageable pageable) {
        Page<OrderDTO> orders = orderService.findOrdersByCompanyId(companyId, pageable);
        return ResponseEntity.ok(orders);
    }
}
