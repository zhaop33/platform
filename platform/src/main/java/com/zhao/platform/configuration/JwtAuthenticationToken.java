package com.zhao.platform.configuration;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author zhaoyanjiang-pc
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 3981518947978158945L;
	
	private UserDetails principal;
	private String credentials;
	private Claims token;
	
	public JwtAuthenticationToken(Claims token) {
		super(Collections.emptyList());
		this.token = token;
	}
	
	public JwtAuthenticationToken(UserDetails principal, Claims token, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.token = token;
	}
	
	@Override
	public void setDetails(Object details) {
		super.setDetails(details);
		this.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
	
	public Claims getToken() {
		return token;
	}
	
}
