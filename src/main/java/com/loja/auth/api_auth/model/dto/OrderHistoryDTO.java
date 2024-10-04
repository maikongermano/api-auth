package com.loja.auth.api_auth.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

import com.loja.auth.api_auth.model.enums.OrderStatus;

@Data
public class OrderHistoryDTO {
    private OrderStatus status;
    private LocalDateTime updatedAt;
}
