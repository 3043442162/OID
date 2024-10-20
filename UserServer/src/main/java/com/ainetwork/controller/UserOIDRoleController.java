package com.ainetwork.controller;


import com.ainetwork.service.UserOIDRoleService;
import com.ainetwork.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户权限管理模块")
@RestController
@RequestMapping("/user_oid_role")
public class UserOIDRoleController {

    @Autowired
    private UserOIDRoleService userOIDRoleService;
    @GetMapping
    public Result queryUserOIDPermission(Integer userId, Integer oidId){
        List<String> permissions = userOIDRoleService.queryUserRole(userId, oidId);
        return Result.ok(permissions);
    }
}
