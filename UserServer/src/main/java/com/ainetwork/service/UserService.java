package com.ainetwork.service;

import com.ainetwork.dto.LoginForm;
import com.ainetwork.entity.User;
import com.ainetwork.mapper.UserMapper;
import com.ainetwork.service.impl.UserServiceImpl;
import com.ainetwork.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;


public interface UserService extends IService<User> {
    Result<?> login(LoginForm loginForm);
    Result<?> register(LoginForm loginForm);
    Result<?> update(LoginForm loginForm);
}
