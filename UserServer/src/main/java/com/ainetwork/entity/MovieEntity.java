package com.ainetwork.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import org.springframework.data.annotation.PersistenceConstructor;



import lombok.Data;


/**
 * Movie实体类，一个neo4j的节点
 */
@Node(labels = "Movie") // 标签名，labels可以缺省
@Data
public class MovieEntity {


	// 定义一个关系（参演）[direction]
	@Relationship(type = "ACTED_IN", direction = Relationship.Direction.INCOMING)
	private List<Roles> actorsAndRoles = new ArrayList<>();
	// 定义一个关系（导演）
	@Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
	private List<PersonEntity> directors = new ArrayList<>();
	// 注意这些关系最终的箭头指向是当前实体，即TargetNode（PersonEntity）->当前定义Relationship的实体（MovieEntity）

	@Id
	@GeneratedValue // Id自增
	private Long id;

	private final String title;

	@Property("tagline") // 映射到neo4j的属性名
	private final String description;

	public MovieEntity(String title, String description) {
		this.id = null;// 生成node时自动生成
		this.title = title;
		this.description = description;
	}

	// 用户指定特定的Id
	public MovieEntity withId(Long id) {
		if (this.id!= null && this.id.equals(id)) {
			return this;
		} else {
			MovieEntity newObject = new MovieEntity(this.title, this.description);
			newObject.id = id;
			return newObject;
		}
	}

}

