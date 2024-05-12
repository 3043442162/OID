package com.ainetwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * oid关系表，存储两个oid之间的关系
 */
@Data
@TableName("OID_relation")
public class OIDRelation {
    /**
     * 唯一id，出现负数时，为未设置TableId
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 父节点id
     */
    @TableField(value = "father_id")
    private Integer fatherId;

//    /**
//     * 当前节点数字
//     */
//    private Integer number;

    /**
     * 子节点id
     */
    @TableField(value = "son_id")
    private Integer sonId;
}
