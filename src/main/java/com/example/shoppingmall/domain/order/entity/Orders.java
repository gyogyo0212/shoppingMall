package com.example.shoppingmall.domain.order.entity;

import java.sql.ConnectionBuilder;
import java.util.ArrayList;
import java.util.List;

import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.global.entity.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;

	@JoinColumn(name = "shipping_address",nullable = false)
	private String shippingAddress;

	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItems> orderItems = new ArrayList<>();


	// 주문 상품 추가 메서드
	public void addOrderItem(OrderItems orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrders(this); // OrderItems의 orders 필드와 연동
	}

	@Builder
	public Orders(User user, String shippingAddress) {
		this.user = user;
		this.shippingAddress = shippingAddress;
		this.orderItems = new ArrayList<>(); // Builder로 생성해도 리스트 초기화
	}

}
