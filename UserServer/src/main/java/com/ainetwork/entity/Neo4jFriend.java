//package com.ainetwork.entity;
//
//import org.springframework.data.neo4j.core.schema.*;
//
///**
// * @Description:
// * @Author:fyc
// * @Date: 2024/11/23
// */
//@RelationshipProperties
//public class Neo4jFriend {
//    @RelationshipId
//    private Long id;
//    @TargetNode
//    private Neo4jOID oidLeft;
//
//    @Property("weight")
//    private Integer weight;
//
//
//    public Neo4jFriend(
//            Neo4jOID oidLeft,
//            Integer weight
//    ){
//        this.oidLeft = oidLeft;
//        this.weight = weight;
//    }
//}
