package com.example.shoppingmall.domain.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.domain.payment.dto.request.PaymentProcessRequestDto;
import com.example.shoppingmall.domain.payment.dto.response.PaymentProcessResponseDto;
import com.example.shoppingmall.domain.payment.service.PaymentService;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.global.dto.ApiResponseDto;
import com.example.shoppingmall.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/{orderId}/payment")
	public ResponseEntity<ApiResponseDto<PaymentProcessResponseDto>> paymentProcess(
		@PathVariable Long orderId,
		@RequestBody PaymentProcessRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
		){
		User user = userDetails.getUser();
		PaymentProcessResponseDto responseDto = paymentService.paymentProcess(orderId,requestDto,user);

		return ResponseEntity.ok(ApiResponseDto.success(responseDto,"결제 성공"));
	}
}
