package com.example.shoppingmall.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.shoppingmall.global.dto.ApiResponseDto;

@RestControllerAdvice//전역예외처리 클래스임을 명시
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponseDto<Object>> handleCustomException(CustomException ex){
		return ResponseEntity
			.status(ex.getErrorCode().getStatus())
			.body(ApiResponseDto.fail(ex.getErrorCode().getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDto<Void>>handleException(Exception ex){
		ex.printStackTrace();
		return ResponseEntity
			.status(500)
			.body(ApiResponseDto.fail("서버 내부 오류 발생"));
	}
}
