package com.example.shoppingmall.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.example.shoppingmall.global.dto.ApiResponseDto;
import com.example.shoppingmall.domain.user.dto.request.LoginRequestDto;
import com.example.shoppingmall.domain.user.dto.request.SignUpRequestDto;
import com.example.shoppingmall.domain.user.dto.response.LoginResponseDto;
import com.example.shoppingmall.domain.user.dto.response.SignUpResponseDto;
import com.example.shoppingmall.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final UserService userService;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<ApiResponseDto<SignUpResponseDto>> signUp(@RequestBody @Valid SignUpRequestDto requestDto){
		SignUpResponseDto response = userService.signup(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(ApiResponseDto.success(response));
	}
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto requestDto){
		LoginResponseDto responseDto = userService.login(requestDto);
		return ResponseEntity.ok(ApiResponseDto.success(responseDto));
	}
}
