package com.example.shoppingmall.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.global.dto.ApiResponseDto;
import com.example.shoppingmall.global.security.UserDetailsImpl;
import com.example.shoppingmall.domain.user.dto.response.UserInfo;
import com.example.shoppingmall.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// 내 정보
	@GetMapping("/me")
	public ResponseEntity<ApiResponseDto<UserInfo>> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		UserInfo userInfo = userService.getMyProfile(userDetails.getUsername());
		return ResponseEntity.ok(ApiResponseDto.success(userInfo));
	}

}

