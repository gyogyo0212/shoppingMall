package com.example.shoppingmall.domain.products.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductsSearchResponseDto {
	private final Long id;

	private final String name;

	private Long price;

	private String category;
}
