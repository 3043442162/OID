package com.ainetwork.service.impl;

import com.ainetwork.entity.UserOidRole;
import com.ainetwork.mapper.UserOIDRoleMapper;
import com.ainetwork.service.RolePermissionService;
import com.ainetwork.service.UserOIDRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserOIDRoleServiceImpl extends ServiceImpl<UserOIDRoleMapper, UserOidRole> implements UserOIDRoleService
{

    @Autowired
    private RolePermissionService rolePermissionService;
    @Override
    public List<String> queryUserRole(Integer userId, Integer oidId) {
        LambdaQueryWrapper<UserOidRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserOidRole::getUserId, userId)
                .eq(UserOidRole::getOidId, oidId)
                .select(UserOidRole::getRoleId);

        List<UserOidRole> roles = getBaseMapper().selectList(wrapper);

        List<String> result = new ArrayList<>();
        for (UserOidRole role :roles
                ) {
             result.addAll(rolePermissionService.queryPermission(role.getRoleId()));
        }

        return result;
    }

    @Override
    public Boolean saveUserOidRole(Integer userId, Integer oidId, Integer roleId) {
        UserOidRole userOidRole = new UserOidRole();
        userOidRole.setUserId(userId);
        userOidRole.setOidId(oidId);
        userOidRole.setRoleId(roleId);
        return save(userOidRole);
    }
}
