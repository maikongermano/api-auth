package com.loja.auth.api_auth.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.loja.auth.api_auth.model.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDTO {
    private String orderId;
    private String clientId;
    private List<OrderProductDTO> products;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<OrderHistoryDTO> history;
}
