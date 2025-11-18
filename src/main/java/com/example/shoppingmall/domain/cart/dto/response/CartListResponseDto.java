package com.example.shoppingmall.domain.cart.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartListResponseDto {
	private List<CartResponseDto> data;
}
