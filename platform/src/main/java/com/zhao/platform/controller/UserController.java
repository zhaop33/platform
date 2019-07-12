package com.zhao.platform.controller;


import com.zhao.platform.entity.UserEntity;
import com.zhao.platform.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String selectFiles(@RequestBody UserEntity user) {
        return String.valueOf(userService.save(user));
    }
}
