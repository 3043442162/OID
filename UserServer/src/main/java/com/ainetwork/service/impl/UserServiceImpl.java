package com.ainetwork.service.impl;

import com.ainetwork.dto.LoginForm;
import com.ainetwork.entity.User;
import com.ainetwork.mapper.UserMapper;
import com.ainetwork.service.UserService;
import com.ainetwork.util.JWTUtil;
import com.ainetwork.util.Result;
import com.ainetwork.util.ResultCode;
import com.ainetwork.vo.LoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Result<?> login(LoginForm loginForm) {
        if(!Optional.ofNullable(loginForm).isPresent()){
            return Result.error(ResultCode.NULL_VALUE, "LoginForm对象不能为空");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getId, loginForm.getAccount());

        User user = getOne(queryWrapper);
        if(!Optional.ofNullable(user).isPresent()) return Result.error("用户不存在");

        if(!BCrypt.checkpw(loginForm.getPassword(), user.getPassword())){
            return Result.error("密码错误");
        }
        user.setPassword("");
        HashMap<String, String> map = new HashMap<>();
        map.put("id", user.getId().toString());
        String token = JWTUtil.getToken(map);
        LoginVo loginVo = new LoginVo(token, user);
        return Result.ok(loginVo);
    }

    @Override
    public Result<?> register(LoginForm loginForm) {
        if(!Optional.ofNullable(loginForm).isPresent()) return Result.error(ResultCode.NULL_VALUE, "LoginForm对象不能为空");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getId, loginForm.getAccount());

        User user = getOne(queryWrapper);
        if(Optional.ofNullable(user).isPresent()) return Result.error("用户已存在");

        user = new User();
        user.setId(loginForm.getAccount());
        user.setPassword(BCrypt.hashpw(loginForm.getPassword()));
        boolean result = save(user);
        return result ? Result.ok() : Result.error();
    }

    @Override
    public Result<?> update(LoginForm loginForm) {
        if(!Optional.ofNullable(loginForm).isPresent()){
            return Result.error(ResultCode.NULL_VALUE, "LoginForm对象不能为空");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getId, loginForm.getAccount());

        User user = getOne(queryWrapper);
        if(!Optional.ofNullable(user).isPresent()) return Result.error("用户不存在");

//        if(!BCrypt.checkpw(loginForm.getPassword(), user.getPassword())){
//            return Result.error();
//        }
        user.setPassword(BCrypt.hashpw(loginForm.getPassword()));
//        user.setPassword("");
//        HashMap<String, String> map = new HashMap<>();
//        map.put("id", user.getId().toString());
        boolean result = updateById(user);
        return result? Result.ok():Result.error();
    }
}
