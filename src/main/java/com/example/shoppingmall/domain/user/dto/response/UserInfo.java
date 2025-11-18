package com.example.shoppingmall.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserInfo {
	private final long id;

	private final String username;

	private final String email;

	private final String address;

}
