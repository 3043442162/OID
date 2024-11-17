package com.ainetwork.controller;

import com.ainetwork.dto.LoginForm;
import com.ainetwork.service.OIDUserService;
import com.ainetwork.util.Result;
import com.ainetwork.util.Verify;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(tags = "用户管理模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private OIDUserService userService;

    @CrossOrigin
//    @Verify
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<?> login(@RequestBody LoginForm loginForm){
        return userService.login(loginForm);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<?> register(@RequestBody @Validated LoginForm loginForm){
        return userService.register(loginForm);
    }

//    @Verify
    @PostMapping("/update")
    @ApiOperation("用户更新")
    public Result<?> update(@RequestBody  LoginForm loginForm){
        return userService.update(loginForm);
    }

}
