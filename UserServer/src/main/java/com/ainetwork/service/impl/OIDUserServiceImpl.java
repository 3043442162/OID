package com.ainetwork.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.ainetwork.dto.LoginForm;
import com.ainetwork.entity.OIDUser;
import com.ainetwork.mapper.OIDUserMapper;
import com.ainetwork.service.OIDUserService;
import com.ainetwork.util.Result;
import com.ainetwork.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OIDUserServiceImpl extends ServiceImpl<OIDUserMapper, OIDUser> implements OIDUserService {
    private LambdaQueryWrapper<OIDUser> queryWrapper;

    @Override
    public Result<?> login(LoginForm loginForm) {
        if(!Optional.ofNullable(loginForm).isPresent()){
            return Result.error(ResultCode.NULL_VALUE, "LoginForm对象不能为空");
        }
        LambdaQueryWrapper<OIDUser> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(OIDUser::getAccount, loginForm.getAccount());

        OIDUser oidUser = getOne(queryWrapper);
        if(!Optional.ofNullable(oidUser).isPresent()) return Result.error("用户不存在");

        if(!BCrypt.checkpw(loginForm.getPassword(), oidUser.getPassword()))
            return Result.error("密码错误");

        Map<String, Object> map = new HashMap<>();
        map.put("id", oidUser.getId());
        StpUtil.login(oidUser.getId());

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        map.put(tokenInfo.getTokenName(), tokenInfo.tokenValue);
        return Result.ok(map);
    }

    @Override
    public Result<?> register(LoginForm loginForm) {
        if(!Optional.ofNullable(loginForm).isPresent()) return Result.error(ResultCode.NULL_VALUE, "LoginForm对象不能为空");
        LambdaQueryWrapper<OIDUser> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(OIDUser::getId, loginForm.getAccount());

        OIDUser oidUser = getOne(queryWrapper);
        if(Optional.ofNullable(oidUser).isPresent()) return Result.error("用户已存在");

        oidUser = new OIDUser();
            oidUser.setAccount(loginForm.getAccount());
            oidUser.setPassword(BCrypt.hashpw(loginForm.getPassword()));
        boolean result = save(oidUser);
        return result ? Result.ok() : Result.error();
    }

    @Override
    public Result<?> update(LoginForm loginForm) {
        if(!Optional.ofNullable(loginForm).isPresent()){
            return Result.error(ResultCode.NULL_VALUE, "LoginForm对象不能为空");
        }
        LambdaQueryWrapper<OIDUser> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(OIDUser::getId, loginForm.getAccount());

        OIDUser OIDUser = getOne(queryWrapper);
        if(!Optional.ofNullable(OIDUser).isPresent()) return Result.error("用户不存在");

//        if(!BCrypt.checkpw(loginForm.getPassword(), OIDUser.getPassword())){
//            return Result.error();
//        }
        OIDUser.setPassword(BCrypt.hashpw(loginForm.getPassword()));
//        OIDUser.setPassword("");
//        HashMap<String, String> map = new HashMap<>();
//        map.put("id", OIDUser.getId().toString());
        boolean result = updateById(OIDUser);
        return result? Result.ok():Result.error();
    }
}
