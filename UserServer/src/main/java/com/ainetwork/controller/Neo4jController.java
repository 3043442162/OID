package com.ainetwork.controller;

import com.ainetwork.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author:fyc
 * @Date: 2024/11/23
 */
@RestController
@RequestMapping("/neo4j")
public class Neo4jController {

//    @Autowired
//    private Neo4jTemplate neo4jTemplate;
    /**
     * 当oid1为oid2服务时，会调用这个方法发生关系让两个node亲密度加一
     * 如果之前没有亲密度，则让生成亲密度
     * @param oid1
     * @param oid2
     * @return
     */
    @GetMapping
    public Result<?> generateRelationShip(String oid1, String oid2){
//        neo4jTemplate.findById(1l, )
        return null;
    }
}
