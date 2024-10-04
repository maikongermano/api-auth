package com.loja.auth.api_auth.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.loja.auth.api_auth.model.dto.OrderDTO;
import com.loja.auth.api_auth.model.dto.OrderHistoryDTO;
import com.loja.auth.api_auth.model.dto.OrderProductDTO;
import com.loja.auth.api_auth.model.entity.Order;
import com.loja.auth.api_auth.model.entity.OrderHistory;
import com.loja.auth.api_auth.model.entity.OrderProduct;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setClientId(order.getClientId());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setDeletedAt(order.getDeletedAt());

        List<OrderProductDTO> productsDTO = order.getProducts().stream()
            .map(this::toOrderProductDTO)
            .collect(Collectors.toList());
        
        dto.setProducts(productsDTO);
        
        List<OrderHistoryDTO> historyDTO = order.getHistory().stream()
            .map(this::toOrderHistoryDTO)
            .collect(Collectors.toList());
        
        dto.setHistory(historyDTO);
        
        return dto;
    }

    public Order toEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setClientId(orderDTO.getClientId());
        order.setStatus(orderDTO.getStatus());
        order.setCreatedAt(orderDTO.getCreatedAt());
        order.setUpdatedAt(orderDTO.getUpdatedAt());
        order.setDeletedAt(orderDTO.getDeletedAt());

        List<OrderProduct> products = orderDTO.getProducts().stream()
            .map(this::toOrderProductEntity)
            .collect(Collectors.toList());
        
        order.setProducts(products);
        
        List<OrderHistory> history = orderDTO.getHistory().stream()
            .map(this::toOrderHistoryEntity)
            .collect(Collectors.toList());
        
        order.setHistory(history);
        
        return order;
    }

    private OrderProductDTO toOrderProductDTO(OrderProduct product) {
        OrderProductDTO dto = new OrderProductDTO();
        dto.setProductId(product.getProductId());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setTotalPrice(product.getTotalPrice());
        return dto;
    }

    private OrderProduct toOrderProductEntity(OrderProductDTO productDTO) {
        OrderProduct product = new OrderProduct();
        product.setProductId(productDTO.getProductId());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setTotalPrice(productDTO.getTotalPrice());
        return product;
    }

    private OrderHistoryDTO toOrderHistoryDTO(OrderHistory history) {
        OrderHistoryDTO dto = new OrderHistoryDTO();
        dto.setStatus(history.getStatus());
        dto.setUpdatedAt(history.getUpdatedAt());
        return dto;
    }

    private OrderHistory toOrderHistoryEntity(OrderHistoryDTO historyDTO) {
        OrderHistory history = new OrderHistory();
        history.setStatus(historyDTO.getStatus());
        history.setUpdatedAt(historyDTO.getUpdatedAt());
        return history;
    }
}
