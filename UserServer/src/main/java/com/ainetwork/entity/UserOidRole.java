package com.ainetwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("user_oid_role")
public class UserOidRole extends Model<UserOidRole> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer oidId;
    @TableField("role_id")
    private Integer roleId;
}
