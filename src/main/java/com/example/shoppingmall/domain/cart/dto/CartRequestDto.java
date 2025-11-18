package com.example.shoppingmall.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CartRequestDto {

	private final Long productId;

	private final Long quantity;
}
