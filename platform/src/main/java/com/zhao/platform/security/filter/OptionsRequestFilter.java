package com.zhao.platform.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

public class OptionsRequestFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getMethod().equals(HttpMethod.OPTIONS.name())) {
			response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
			response.setHeader("Access-Control-Allow-Headers", response.getHeader("Access-Control-Request-Headers"));
			return;
		}
		filterChain.doFilter(request, response);
	}

}
