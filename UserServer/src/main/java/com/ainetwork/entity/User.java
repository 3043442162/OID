package com.ainetwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("oid_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "password")
    private String password;
}
