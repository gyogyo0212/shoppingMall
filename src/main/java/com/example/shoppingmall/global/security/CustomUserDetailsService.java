package com.example.shoppingmall.global.security;


// DB에서 사용자 정보를 로드하는 클래스

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.shoppingmall.global.exception.CustomException;
import com.example.shoppingmall.global.exception.ErrorCode;
import com.example.shoppingmall.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username){
		return userRepository.findByUsername(username)
			.map(UserDetailsImpl::new)
			.orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
	}

}
