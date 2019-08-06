package com.zhao.platform.security.handler;

import com.zhao.platform.security.token.JwtAuthenticationToken;
import com.zhao.platform.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * @author zhaoyanjiang-pc
 */
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler{
	/* 刷新间隔5分钟 */
	private static final int tokenRefreshInterval = 300;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Claims jwt = ((JwtAuthenticationToken)authentication).getToken();
		boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt());
		if(shouldRefresh) {
			UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            String newToken = JwtUtils.createJWT(userDetails.getUsername());
            response.setHeader(JwtUtils.HEAD_TOKEN, newToken);
        }	
	}

	protected boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }
}
