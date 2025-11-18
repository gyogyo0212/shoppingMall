package com.example.shoppingmall.domain.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.domain.cart.dto.CartRequestDto;
import com.example.shoppingmall.domain.cart.service.CartService;
import com.example.shoppingmall.global.dto.ApiResponseDto;
import com.example.shoppingmall.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CartController {

	private final CartService cartService;
	//장바구니 상품 등록
	@PostMapping("/cart")
	public ResponseEntity<ApiResponseDto<Void>> addToCart(
		@RequestBody CartRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	){
		long userId = userDetails.getUser().getId();
		cartService.addToCart(requestDto,userId);

		return ResponseEntity.ok(ApiResponseDto.success(null));
	}

}
