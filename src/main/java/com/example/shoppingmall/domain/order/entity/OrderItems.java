package com.example.shoppingmall.domain.order.entity;

import com.example.shoppingmall.domain.products.entity.Products;
import com.example.shoppingmall.global.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "orders_id", nullable = false)
	private Orders orders;

	@ManyToOne
	@JoinColumn(name = "products_id", nullable = false)
	private Products products;

	@Enumerated(EnumType.STRING)
	private OrdersStatus status;


	public void setOrders(Orders orders) {
		this.orders = orders;
	}
}
