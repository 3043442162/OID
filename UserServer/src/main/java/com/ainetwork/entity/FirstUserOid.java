package com.ainetwork.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("first_user_oid")
public class FirstUserOid {
    @TableId(type = IdType.AUTO)
    private Integer id ;

    private Integer userId;
    private Integer oidId;
}
