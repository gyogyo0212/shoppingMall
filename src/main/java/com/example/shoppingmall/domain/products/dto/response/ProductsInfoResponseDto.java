package com.example.shoppingmall.domain.products.dto.response;

import com.example.shoppingmall.domain.products.entity.Products;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductsInfoResponseDto {
	private final long id;

	private final String name;

	private final long price;

	public ProductsInfoResponseDto(Products product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
	}

}
