package com.example.shoppingmall.domain.order.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrdersCreatRequestDto {

	private final String shippingAddress;

	private final List<OrderItemDto> items;
}
