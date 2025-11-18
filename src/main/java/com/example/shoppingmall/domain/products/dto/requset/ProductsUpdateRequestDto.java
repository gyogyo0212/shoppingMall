package com.example.shoppingmall.domain.products.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductsUpdateRequestDto {
	private final long price;
	private final long stock;
}
