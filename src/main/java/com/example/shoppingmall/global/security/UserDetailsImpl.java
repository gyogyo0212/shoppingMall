package com.example.shoppingmall.global.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.shoppingmall.domain.user.entity.User;


//스프링 시큐리티가 인증 객체를 다룰 수 있도록 User 엔티티를 감싸는 클래스
public class UserDetailsImpl implements UserDetails {

	private final User user;

	public UserDetailsImpl(User user){
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public Collection<? extends GrantedAuthority>getAuthorities(){
		return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
	}
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { return true; }

	@Override
	public boolean isAccountNonLocked() { return true; }

	@Override
	public boolean isCredentialsNonExpired() { return true; }

	@Override
	public boolean isEnabled() { return true; }
}
