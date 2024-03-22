package com.ainetwork.service;

import com.ainetwork.entity.UserOID;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserOIDService extends IService<UserOID> {
    /**
     * 查询当前用户所持有的OID
     * @param user
     * @return
     */
    List<UserOID> queryUserOwnerOID(Integer user);
}
