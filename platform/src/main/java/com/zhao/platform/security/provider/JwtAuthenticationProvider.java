package com.zhao.platform.security.provider;

import com.zhao.platform.security.token.JwtAuthenticationToken;
import com.zhao.platform.service.IUserService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import java.util.Calendar;

/**
 * @author zhaoyanjiang-pc
 */
public class JwtAuthenticationProvider implements AuthenticationProvider{
	
	private IUserService userService;
	
	public JwtAuthenticationProvider(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Claims jwt = ((JwtAuthenticationToken)authentication).getToken();
		if(jwt.getExpiration().before(Calendar.getInstance().getTime())) {
			throw new NonceExpiredException("Token expires");
		}
		String username = jwt.getSubject();
		UserDetails user = userService.loadUserByUsername(username);
		if(user == null) {
			throw new NonceExpiredException("Token expires");
		}
		JwtAuthenticationToken token = new JwtAuthenticationToken(user, jwt, user.getAuthorities());
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}

}
