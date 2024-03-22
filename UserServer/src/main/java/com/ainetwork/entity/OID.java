package com.ainetwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * oid实体
 * 通过唯一标识id或者
 * 它以及它所有父节点共同组成的id进行标识
 */
@Data
@TableName("OID")
public class OID {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 当前节点oid
     */
    @NotNull
    private Integer oid;
    /**
     * OID描述信息
     */
//    @NotBlank
    @TableField("`describe`")
    private String describe;

    /**
     * oid对应ip地址
     */
    @NotBlank
    private String ipAddress;
}
