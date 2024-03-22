package com.ainetwork.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * oid关系表，存储两个oid之间的关系
 */
@Data
@TableName("OID_relation")
public class OIDRelation {
    /**
     * 唯一id
     */
    private Integer id;
    /**
     * 父节点id
     */
    private Integer fatherId;

//    /**
//     * 当前节点数字
//     */
//    private Integer number;

    /**
     * 子节点id
     */
    private Integer sonId;
}
