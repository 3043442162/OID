package com.ainetwork.service.impl;

import com.ainetwork.entity.UserOID;
import com.ainetwork.mapper.UserOIDMapper;
import com.ainetwork.service.UserOIDService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOIDServiceImpl extends ServiceImpl<UserOIDMapper, UserOID> implements UserOIDService {
    @Autowired
    private UserOIDMapper userOIDMapper;

    @Override
    public List<UserOID> queryUserOwnerOID(Integer user) {
        LambdaQueryWrapper<UserOID> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserOID::getUserId, user);
        wrapper.select(UserOID::getOid);

        List<UserOID> userOIDS = userOIDMapper.selectList(wrapper);
        return userOIDS;
    }
}
