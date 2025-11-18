package com.example.shoppingmall.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


	INVALID_INPUT(HttpStatus.BAD_REQUEST,"잘못된 입력값"),

	// auth
	EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"중복된 이메일"),
	USER_NAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"이미 있는 이름입니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND,"유저를 찾을 수 없습니다." ),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다." ),

	//products
	PRODUCT_EXISTS(HttpStatus.BAD_REQUEST,"중복된 상품입니다." ),
	PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND,"상품을 찾을 수 없습니다." ),
	OUT_OF_STOCK(HttpStatus.BAD_REQUEST,"재고가 없습니다." ),

	//cart
	CART_EXISTS(HttpStatus.BAD_REQUEST,"이미 등록된 상품입니다." ),

	//order
	INVALID_ORDER_ITEMS(HttpStatus.BAD_REQUEST,"상품 목록이 비어있습니다." ),
	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND," 주문 목록이 없습니다."),

	//payment
	INVALID_PAYMENT_AMOUNT(HttpStatus.BAD_REQUEST,"결제 금액이 올바르지 않습니다. " );

	//


	private final HttpStatus status;
	private final String message;
}
