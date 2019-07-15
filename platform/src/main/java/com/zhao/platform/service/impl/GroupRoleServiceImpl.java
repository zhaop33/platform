package com.zhao.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.platform.entity.GroupEntity;
import com.zhao.platform.entity.GroupRoleEntity;
import com.zhao.platform.mapper.GroupMapper;
import com.zhao.platform.mapper.GroupRoleMapper;
import com.zhao.platform.service.IGroupRoleService;
import com.zhao.platform.service.IGroupService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaoyj
 * @since 2019-07-12
 */
@Service
public class GroupRoleServiceImpl extends ServiceImpl<GroupRoleMapper, GroupRoleEntity> implements IGroupRoleService {

}
