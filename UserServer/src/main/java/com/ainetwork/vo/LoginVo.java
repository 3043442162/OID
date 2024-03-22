package com.ainetwork.vo;

import com.ainetwork.entity.User;
import lombok.Data;

@Data
public class LoginVo {
    private String token;
    private User info;

    public LoginVo(String token, User info) {
        this.token = token;
        this.info = info;
    }
}
