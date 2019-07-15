package com.zhao.platform.filter;

import com.zhao.platform.entity.PermissionEntity;
import com.zhao.platform.service.IPermissionService;
import com.zhao.platform.service.IUserService;
import com.zhao.platform.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 权限资源管理器
 * 为权限决断器提供支持
 * @author Exrickx
 */
@Slf4j
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;

    private Map<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有操作请求权限
     */
    public void loadResourceDefine(){

        map = new HashMap<>(16);
        Collection<ConfigAttribute> configAttributes;
        ConfigAttribute cfg;
        // 获取启用的权限操作请求
        List<PermissionEntity> permissions = permissionService.list();
        for(PermissionEntity permission : permissions) {
            configAttributes = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            //作为MyAccessDecisionManager类的decide的第三个参数
            configAttributes.add(cfg);
            //用权限的path作为map的key，用ConfigAttribute的集合作为value
            map.put(permission.getId(), configAttributes);
        }
    }

    /**
     * 判定用户请求的url是否在权限表中
     * 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        if(map == null){
            loadResourceDefine();
        }
        //Object中包含用户请求request
        HttpServletRequest request = ((FilterInvocation) o).getRequest();
        String token = request.getHeader(JwtUtils.HEAD_TOKEN);
        String userId = null;
        try{
            userId = JwtUtils.parseUserId(token);
        }catch (Exception e){
            return getAllConfigAttributes();
        }

        return map.get(userId);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
