package com.loja.auth.api_auth.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.loja.auth.api_auth.model.enums.OrderStatus;

@Data
@Entity
@Table(name = "order_history")
public class OrderHistory {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
	
	@ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;  // Status do pedido nesse momento
	
	@Column(nullable = false)
    private LocalDateTime updatedAt; // Data dessa alteração no status
	
	@PrePersist
    @PreUpdate
    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}
