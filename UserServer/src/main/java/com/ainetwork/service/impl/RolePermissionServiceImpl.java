package com.ainetwork.service.impl;

import com.ainetwork.entity.RolePermission;
import com.ainetwork.enums.PermissionEnum;
import com.ainetwork.mapper.RolePermissionMapper;
import com.ainetwork.service.RolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService{

    @Override
    public List<String> queryPermission(Integer roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(RolePermission::getRoleId, roleId);

        List<RolePermission> rolePermissions = getBaseMapper().selectList(wrapper);

        if(rolePermissions == null) {
            List<String> permissions = new ArrayList<>();
            permissions.add(PermissionEnum.QUERY_NODE.getDesc());
            return permissions;
        }
        return rolePermissions.stream().map(RolePermission::getPermission).map(PermissionEnum::getDesc).collect(Collectors.toList());
    }
}
