package com.ainetwork.vo;

import lombok.Data;

import java.util.List;

/**
 * @author  fyc
 * 用于前端展示树状结构的vo
 */
@Data
public class TreeVo {
    /**
     * 唯一id
     */
    Integer id;
    /**
     * 存放信息
     */
    String label;
    /**
     * 子节点集合
     */
    List<TreeVo> children;
}
