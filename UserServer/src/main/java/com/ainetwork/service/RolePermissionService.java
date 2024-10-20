package com.ainetwork.service;

import com.ainetwork.entity.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RolePermissionService extends IService<RolePermission> {
    /**
     * 根据用户角色返回权限列表
     * @param roleId 用户角色id
     * @return 权限列表
     */
    List<String> queryPermission(Integer roleId);
}
