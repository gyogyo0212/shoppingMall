package com.example.shoppingmall.domain.cart.entity;

import com.example.shoppingmall.global.entity.BaseTimeEntity;
import com.example.shoppingmall.domain.products.entity.Products;
import com.example.shoppingmall.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Cart extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "products_id",nullable = false)
	private Products products;

	@Column(nullable = false)
	private long quantity;

	@Builder
	public Cart(User user, Products products,long quantity) {
		this.user = user;
		this.products = products;
		this.quantity = quantity;
	}

	public void addQuantity(long quantity) {
		this.quantity += quantity;
	}
}
