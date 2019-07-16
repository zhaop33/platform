package com.zhao.platform.handler;

import com.zhao.platform.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaoyanjiang-pc
 */
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String token = JwtUtils.createJWT(userDetails.getUsername());
        response.setHeader(JwtUtils.HEAD_TOKEN, token);
    }
}
