package com.example.shoppingmall.domain.products.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor

public class ProductsResponseDto {

	private final long id;

	private final String name;

	private final long price;

	private final long stock;

}
