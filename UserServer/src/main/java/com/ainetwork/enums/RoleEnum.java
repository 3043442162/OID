package com.ainetwork.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;

/**
 * 角色枚举
 */
@Getter
public enum RoleEnum  {
    OWNER(0,"OWNER"),
    OTHER(1,"OTHER");

    RoleEnum(Integer value, String desc) {
        this.roleId = value;
        this.desc = desc;
    }
    @EnumValue
    @TableField("role_id")
    private Integer roleId;
    @JsonValue
    private String desc;

    @Override
    public String toString() {
        return this.desc;
    }
}
