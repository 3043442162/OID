package com.ainetwork.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_oid")
public class UserOID {
    @TableId
    Integer id;

    /**
     * 用户账户
     */
    Integer userId;

    /**
     * oid 对应id
     */
    Integer oid;
}
