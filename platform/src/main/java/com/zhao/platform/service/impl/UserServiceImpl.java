package com.zhao.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.platform.entity.*;
import com.zhao.platform.mapper.UserMapper;
import com.zhao.platform.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.platform.utils.JwtUtils;
import com.zhao.platform.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaoyj
 * @since 2019-07-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    @Autowired private IUserRoleService userRoleService;
    @Autowired private IRolePermissionService rolePermissionService;
    @Autowired private IUserGroupService userGroupService;
    @Autowired private IGroupRoleService groupRoleService;
    @Autowired private IPermissionService permissionService;
    @Autowired private IUserAuthsService userAuthsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userDetails = this.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getName,username).last("limit 1"));
        if(userDetails == null){
            throw new UsernameNotFoundException("用户名没有发现");
        }
        List<String> permissions = this.getPermissionByUserId(username);
        List<GrantedAuthority> simpleGrantedAuthorities = new ArrayList<>(permissions.size());
        for (String permission : permissions) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(permission));
        }
        userDetails.setAuthorities(simpleGrantedAuthorities);
        return userDetails;
    }

    @Override
    public List<String> getPermissionByUserId(String id) {
        List<String> roleIds = getRoleByUserId(id);
        final List<String> permission = Collections.synchronizedList(new ArrayList<>());
        if(!CollectionUtils.isEmpty(roleIds)){
            Optional.ofNullable(rolePermissionService.list(new QueryWrapper<RolePermissionEntity>().lambda().in(RolePermissionEntity::getRoleId,roleIds))).ifPresent(rolePermissions -> {
                List<String> permissionIds = rolePermissions.stream().map(RolePermissionEntity::getPermissionId).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(permissionIds)){
                    Optional.ofNullable(permissionService.list(new QueryWrapper<PermissionEntity>().lambda().in(PermissionEntity::getId,permissionIds))).ifPresent(list -> {
                        list.stream().map(PermissionEntity::getName).forEach(permission::add);
                    });
                }
            });
        }
        return permission;
    }

    @Override
    public List<String> getRoleByUserId(String id) {
        Set<String> roleIds = new HashSet<>();
        Optional.ofNullable(userRoleService.list(new QueryWrapper<UserRoleEntity>().lambda().eq(UserRoleEntity::getUserId,id))).ifPresent(list -> {
            list.forEach(l -> roleIds.add(l.getId()));
        });
        Optional.ofNullable(userGroupService.list(new QueryWrapper<UserGroupEntity>().lambda().eq(UserGroupEntity::getUserId,id))).ifPresent(list -> {
            List<String> groupIds = list.stream().map(UserGroupEntity::getGroupId).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(groupIds)){
                Optional.ofNullable(groupRoleService.list(new QueryWrapper<GroupRoleEntity>().lambda().in(GroupRoleEntity::getGroupId,groupIds))).ifPresent(groupRoleList -> {
                    groupRoleList.forEach(groupRole -> roleIds.add(groupRole.getRoleId()));
                });
            }
        });
        List<String> roleIdList = new ArrayList<>(roleIds);
        return roleIdList;
    }

    @Override
    public String login(LoginVO loginVO) {
        Optional.ofNullable(this.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getName,loginVO.getUserName()).last("limit 1"))).ifPresent(userEntity -> {
            Optional.ofNullable(userAuthsService.getOne(new QueryWrapper<UserAuthsEntity>().lambda().eq(UserAuthsEntity::getUserId,userEntity.getId()).eq(UserAuthsEntity::getIdentityType,loginVO.getLoginType()).eq(UserAuthsEntity::getCredential,loginVO.getCert()))).ifPresent(userAuthsEntity -> {
                JwtUtils.createJWT(userAuthsEntity.getUserId());
            });
        });
        return null;
    }
}
