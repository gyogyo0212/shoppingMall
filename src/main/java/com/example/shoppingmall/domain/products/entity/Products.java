package com.example.shoppingmall.domain.products.entity;

import com.example.shoppingmall.global.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class Products extends BaseTimeEntity {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Column(name = "product_name")
	private String name;

	@NotNull
	@Column
	private long price;

	@NotNull
	@Column
	private long stock;

	@NotNull
	@Column
	private String category;

	@Builder
	public Products(long id, String name, long price, long stock, String category){
		this.id =id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.category = category;
	}

	public void update(long price, long stock) {
		this.price = price;
		this.stock = stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}
}
