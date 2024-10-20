package com.ainetwork.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 权限枚举类
 */
@Getter
public enum PermissionEnum {
    ADD_NODE(1,"ADD_NODE"),
    UPDATE_NODE(2,"UPDATE_NODE"),
    QUERY_NODE(3,"QUERY_NODE"),
    DELETE_NODE(4,"DELETE_NODE");

    PermissionEnum(Integer permission_id, String s) {
        this.permission_id=permission_id;
        this.desc=s;
    }
    @EnumValue
    private Integer permission_id;
    @JsonValue
    private String desc;

    @Override
    public String toString() {
        return this.desc;
    }
}
