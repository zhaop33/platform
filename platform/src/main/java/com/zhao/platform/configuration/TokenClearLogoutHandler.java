package com.zhao.platform.configuration;

import com.zhao.platform.cache.InvalidTokenCache;
import com.zhao.platform.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author zhaoyanjiang-pc
 */
@Slf4j
public class TokenClearLogoutHandler implements LogoutHandler {


	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		clearToken(authentication);
        String authInfo = request.getHeader("Authorization");
        if(StringUtils.isNoneBlank(authInfo)){
            InvalidTokenCache.invalid(authInfo);
        }
	}
	
	protected void clearToken(Authentication authentication) {
		if(authentication == null) {
			return;
		}
		UserDetails user = (UserDetails)authentication.getPrincipal();
		if(user!=null && user.getUsername()!=null) {
			log.info("logout !");
		}
	}

}
