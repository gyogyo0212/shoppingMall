package com.example.shoppingmall.domain.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartResponseDto {
	private final long productsId;

	private final long quantity;

}
