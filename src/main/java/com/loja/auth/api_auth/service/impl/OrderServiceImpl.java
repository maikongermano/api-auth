package com.loja.auth.api_auth.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loja.auth.api_auth.mappers.OrderMapper;
import com.loja.auth.api_auth.model.dto.OrderDTO;
import com.loja.auth.api_auth.model.entity.Order;
import com.loja.auth.api_auth.model.enums.OrderStatus;
import com.loja.auth.api_auth.repository.OrderRepository;
import com.loja.auth.api_auth.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Ordem não encontrada"));
        return orderMapper.toDTO(order);
    }
    
    @Override
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
        	Order order = orderOptional.get();
            order.setStatus(newStatus);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Ordem " + orderId + " não encontrada");
        }
    }
    
    @Override
    public Page<OrderDTO> findOrdersByCompanyId(Long companyId, Pageable pageable) {
        return orderRepository.findByCompanyId(companyId, pageable).map(orderMapper::toDTO);
    }

}
