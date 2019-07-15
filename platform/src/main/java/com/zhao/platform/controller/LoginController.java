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
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登陆", notes = "登陆")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultInfo selectFiles(@RequestBody LoginVO loginVO) {
        return ResultInfo.getInstance();
    }
}
