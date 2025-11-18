package com.example.shoppingmall.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
	private String username;

	private String password;

	private String email;

	private String address;
}
