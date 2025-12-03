package com.example.shoppingmall.domain.order.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrdersListResponseDto {

	// 여러 주문을 담을 리스트
	private final List<OrdersCreatResponseDto> orders;
}
