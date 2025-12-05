package com.example.shoppingmall.global.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// Jwt를 생성하고 검증하는 핵심 클래스

@Component
public class JwtProvider {

	@Value("${JWT_SECRET}")
	private String secretKey;

	private final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 30; //  30분

	// Access Token 생성
	public String createAccessToken(String username, String email) {
		Date now = new Date();
		return Jwts.builder()
				   .setSubject(username)
				   .claim("email",email)
				   .setIssuedAt(now)
				   .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION))
				   .signWith(SignatureAlgorithm.HS256, secretKey)
				   .compact();
	}
	// 토큰 유효성 검사
	public boolean validateToken(String token){
		try{
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 토큰에서 사용자 이름 추출
	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				   .setSigningKey(secretKey)
				   .parseClaimsJws(token)
				   .getBody()
				   .getSubject();
	}
}
