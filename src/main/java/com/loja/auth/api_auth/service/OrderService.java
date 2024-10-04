package com.loja.auth.api_auth.service;

import com.loja.auth.api_auth.model.dto.OrderDTO;
import com.loja.auth.api_auth.model.enums.OrderStatus;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrderById(Long orderId);
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
}
