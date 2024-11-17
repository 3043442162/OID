package com.ainetwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("oid_xml")
public class OIDXml {
    @TableId(type = IdType.AUTO)
    private int id;
//    private int oidId;
    // 实际存的
    private String xmlUrl;
    // 给用户看的
    private String xmlName;
    private Integer oidUserId;
}
