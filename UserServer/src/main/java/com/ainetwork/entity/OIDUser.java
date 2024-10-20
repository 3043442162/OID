package com.ainetwork.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("oid_user")
public class OIDUser {
    @TableId(type = IdType.AUTO)
    Integer id;

    /**
     * 用户账户
     */
    Long account;

    /**
     * 密码
     */
    String password;
}
