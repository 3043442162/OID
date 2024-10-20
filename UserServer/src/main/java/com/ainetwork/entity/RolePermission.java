package com.ainetwork.entity;

import com.ainetwork.enums.PermissionEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色对应权限
 */
@Data
@TableName("role_permission")
public class RolePermission {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roleId;
    @TableField("permission_id")
    private PermissionEnum permission;
}
