package com.zhao.platform.controller;

import com.zhao.platform.component.ResultInfo;
import com.zhao.platform.entity.UserEntity;
import com.zhao.platform.service.IUserService;
import com.zhao.platform.vo.LoginVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoyanjiang-pc
 */
@RestController
@RequestMapping("/")
public class LoginController {


    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登陆", notes = "登陆")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResultInfo login(@RequestBody LoginVO vo) {
        return null;
    }
    @ApiOperation(value = "登出", notes = "登出")
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResultInfo logout() {
        return null;
    }
}
