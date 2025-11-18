package com.example.shoppingmall.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // 👈 null 필드는 JSON에 표시하지 않음
public class ApiResponseDto<T>{
	private boolean result;
	private String message;
	private T data;

	public static <T> ApiResponseDto<T> success(T data){
		return ApiResponseDto.<T>builder()
							 .result(true)
							 .data(data)
							 .build();
	}

	public static <T> ApiResponseDto<T> success(T data,String message){
		return ApiResponseDto.<T>builder()
							 .result(true)
							 .data(data)
							 .message(message)
							 .build();
	}


	public static <T> ApiResponseDto<T> fail(String message){
		return ApiResponseDto.<T>builder()
							 .result(false)
							 .message(message)
							 .build();
	}

}
