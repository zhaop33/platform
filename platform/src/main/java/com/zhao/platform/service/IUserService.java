package com.zhao.platform.service;

import com.zhao.platform.entity.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.platform.vo.LoginVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaoyj
 * @since 2019-07-12
 */
public interface IUserService extends IService<UserEntity>, UserDetailsService {

    /**
     * 根据用户ID获取权限
     * @param id
     * @return
     */
    List<String> getPermissionByUserId(String id);

    /**
     * 根据用户名获取角色
     * @param id
     * @return
     */
    List<String> getRoleByUserId(String id);

    /**
     * 登陆
     * @param loginVO
     * @return
     */
    String login(LoginVO loginVO);
}
