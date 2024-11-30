//package com.ainetwork.entity;
//
//import lombok.Data;
//import org.springframework.data.neo4j.core.schema.Id;
//import org.springframework.data.neo4j.core.schema.Node;
//import org.springframework.data.neo4j.core.schema.Property;
//import org.springframework.data.neo4j.core.schema.Relationship;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Description:
// * @Author:fyc
// * @Date: 2024/11/23
// */
//@Node(labels = "OID") // 标签名，labels可以缺省
//@Data
//public class Neo4jOID {
//    // 定义一个友谊关系
//    @Relationship(type = "friend", direction = Relationship.Direction.INCOMING)
//    private List<Neo4jFriend> friend = new ArrayList<>();
//    @Id
//    private Long id;
//    @Property("tagline")
//    private String oidFlag;
//// 映射到neo4j中的属性名
//
//    public Neo4jOID(Long id, String oidFlag) {
//        this.id = id;
//        this.oidFlag = oidFlag;
//    }
//
//
//}
