package com.ainetwork.vo;

import com.ainetwork.entity.OIDUser;
import com.ainetwork.entity.OIDUser;
import lombok.Data;

@Data
public class LoginVo {
    private String token;
    private OIDUser info;

    public LoginVo(String token, OIDUser info) {
        this.token = token;
        this.info = info;
    }
}
