package com.example.shoppingmall.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

//스프링 시큐리티 설정파일 - JWT 필터 등록하는
@Configuration // 설정파일임을 명시
@EnableWebSecurity 	// 스프링 시큐리티 활성화
@RequiredArgsConstructor	//생성자 주입
public class SecurityConfig {
	//필터 주입
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
		throws Exception{
		//CSRF 비활성화
		http.csrf(AbstractHttpConfigurer::disable)

			// 세션 사용안함 (JWT 사용시 필수)
			.sessionManagement(session->
								   session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

			// 인가 규칙 설정
			http.authorizeHttpRequests(auth->auth
				.requestMatchers("/api/admin/**").hasRole("ADMIN")  // 관리자 전용
				.requestMatchers("/api/auth/**").permitAll() // 회원가입, 로그인 인증 필요 없슴
				.requestMatchers("/api/products/**").permitAll() // 상품목록 조회 인증 필요없슴
				.anyRequest().authenticated()	// 그 외 요청은 인증 필요
			)
				//JWT 필터 추가
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
