package com.example.shoppingmall.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrdersCreatResponseDto {

	private final Long orderId;
	private final int totalItems;

}
