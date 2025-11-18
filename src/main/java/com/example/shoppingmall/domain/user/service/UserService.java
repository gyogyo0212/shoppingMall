package com.example.shoppingmall.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.shoppingmall.global.exception.CustomException;
import com.example.shoppingmall.global.exception.ErrorCode;
import com.example.shoppingmall.global.security.JwtProvider;
import com.example.shoppingmall.domain.user.dto.request.LoginRequestDto;
import com.example.shoppingmall.domain.user.dto.request.SignUpRequestDto;
import com.example.shoppingmall.domain.user.dto.response.LoginResponseDto;
import com.example.shoppingmall.domain.user.dto.response.SignUpResponseDto;
import com.example.shoppingmall.domain.user.dto.response.UserInfo;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.domain.user.entity.UserRole;
import com.example.shoppingmall.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	//회원가입
	public SignUpResponseDto signup(SignUpRequestDto requestDto) {
		if (userRepository.existsByUsername(requestDto.getUsername())) {
			throw new CustomException(ErrorCode.USER_NAME_ALREADY_EXIST);
		}
		if (userRepository.existsByEmail(requestDto.getEmail())) {
			throw new CustomException(ErrorCode.EMAIL_ALREADY_EXIST);
		}
		UserRole role = UserRole.USER;


		// 예: 이메일이 특정 도메인일 경우 자동 관리자 지정
		if (requestDto.getEmail().equals("admin@mall.com")) {
			role = UserRole.ADMIN;
		}

		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

		User user = User.builder()
						.username(requestDto.getUsername())
						.password(encodedPassword)
						.email((requestDto.getEmail()))
						.address(requestDto.getAddress())
						.role(UserRole.USER)
						.build();

		userRepository.save(user);

		return new SignUpResponseDto(
			user.getId()
		);
	}

	//로그인
	public LoginResponseDto login(LoginRequestDto requestDto){
		User user = userRepository.findByUsername(requestDto.getUsername())
										.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		}
		String token = jwtProvider.createAccessToken(user.getUsername(), user.getEmail());

		//응답 반환
		return new LoginResponseDto(token);
	}

	//상세 조회
	public UserInfo getMyProfile(String username){
		User user = userRepository.findByUsername(username)
			.orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

		return new UserInfo(user.getId(),user.getUsername(),user.getEmail(),user.getAddress());
	}

}

