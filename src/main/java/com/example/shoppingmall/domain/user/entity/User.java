package com.example.shoppingmall.domain.user.entity;

import com.example.shoppingmall.domain.cart.entity.Cart;
import com.example.shoppingmall.global.entity.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart;

	@Column(name = "user_name",nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;
	@Email
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String address;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@Builder
	public User(long id, String username, String password, String email, String address, UserRole role){
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.role = role;
	}

}
