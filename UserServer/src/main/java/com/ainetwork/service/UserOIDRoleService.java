package com.ainetwork.service;

import com.ainetwork.entity.UserOidRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserOIDRoleService extends IService<UserOidRole> {
    /**
     * 根据user_id，oid_id查询该用户对于该OID所属权限
     * @param userId 用户id
     * @param oidId oid唯一标识
     * @return 角色id
     */
    List<String> queryUserRole(Integer userId, Integer oidId);

    /**
     * 将user_id,oid_id,roleId 保存到数据库中
     */
    Boolean saveUserOidRole(Integer userId,Integer oidId, Integer roleId);
}
