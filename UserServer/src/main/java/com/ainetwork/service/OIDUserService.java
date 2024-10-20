package com.ainetwork.service;

import com.ainetwork.dto.LoginForm;

import com.ainetwork.entity.OIDUser;
import com.ainetwork.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;


public interface OIDUserService extends IService<OIDUser> {
    Result<?> login(LoginForm loginForm);
    Result<?> register(LoginForm loginForm);
    Result<?> update(LoginForm loginForm);
}
