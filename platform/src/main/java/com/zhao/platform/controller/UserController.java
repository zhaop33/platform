package com.zhao.platform.controller;


import com.zhao.platform.component.ResultInfo;
import com.zhao.platform.entity.UserEntity;
import com.zhao.platform.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaoyj
 * @since 2019-07-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private IUserService userService;

    @ApiOperation(value = "添加", notes = "添加")
    @RequestMapping(value = "", method = RequestMethod.POST)

    public ResultInfo selectFiles(@RequestBody UserEntity user) {

        return ResultInfo.getInstance().setSingleData(userService.save(user));
    }
    @ApiOperation(value = "获取角色", notes = "获取角色")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @PreAuthorize("hasRole('admin')")
    public ResultInfo getRoleByUserId(@RequestParam String userId) {
        return ResultInfo.getInstance().setSingleData(userService.getRoleByUserId(userId));
    }
    @ApiOperation(value = "获取权限", notes = "获取权限")
    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    @PreAuthorize("hasRole('group')")
    public ResultInfo getPermissionByUserId(@RequestParam String userId) {
        return ResultInfo.getInstance().setSingleData(userService.getPermissionByUserId(userId));
    }
}
