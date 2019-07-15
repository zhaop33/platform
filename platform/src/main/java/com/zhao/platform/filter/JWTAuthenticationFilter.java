package com.zhao.platform.filter;

import com.zhao.platform.service.IUserService;
import com.zhao.platform.utils.JwtUtils;
import com.zhao.platform.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author zhaoyanjiang-pc
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private IUserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, IUserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(JwtUtils.HEAD_TOKEN);
        if (StringUtils.isBlank(token)) {
            chain.doFilter(request, response);
//            ResponseUtil.out(response, ResponseUtil.resultMap(false, HttpStatus.UNAUTHORIZED.value(), "登录已失效，请重新登录"));
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            e.printStackTrace();
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token, HttpServletResponse response) {

        // 用户名
        String username = null;
        // 权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        try {
            username = JwtUtils.parseUserId(token);
        } catch (Exception e) {
            ResponseUtil.out(response, ResponseUtil.resultMap(false, HttpStatus.UNAUTHORIZED.value(), "登录已失效，请重新登录"));
            return null;
        }
        List<String> permissions = userService.getPermissionByUserId(username);
        User principal = new User(username, "", permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }
}

