package com.example.shoppingmall.domain.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.domain.order.dto.request.OrdersCreatRequestDto;
import com.example.shoppingmall.domain.order.dto.response.OrdersListResponseDto;
import com.example.shoppingmall.domain.order.service.OrdersService;
import com.example.shoppingmall.domain.order.dto.response.OrdersCreatResponseDto;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.global.dto.ApiResponseDto;
import com.example.shoppingmall.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

	private final OrdersService ordersService;

	@PostMapping("/orders")
	public ResponseEntity<ApiResponseDto<OrdersCreatResponseDto>> creatOrders(
		@RequestBody OrdersCreatRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	){
		User user = userDetails.getUser();
		OrdersCreatResponseDto orders = ordersService.creatOrders(requestDto, user);
		return ResponseEntity.ok(ApiResponseDto.success(orders,"주문 접수 완료"));
	}


	@GetMapping("/orders")
	public ResponseEntity<ApiResponseDto<OrdersListResponseDto>> ordersList (
		@AuthenticationPrincipal UserDetailsImpl userDetails
	){
		User user = userDetails.getUser();
		OrdersListResponseDto ordersList = ordersService.ordersList(user);
		return ResponseEntity.ok(ApiResponseDto.success(ordersList,"목록 조회 성공"));
	}
}
