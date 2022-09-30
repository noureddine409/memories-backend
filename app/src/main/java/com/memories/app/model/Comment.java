package com.memories.app.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Node
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Comment extends GenericEntity {
	private String comment;
	@Relationship(type = "CREATE", direction = Direction.INCOMING)
	private User owner;
	@Relationship(type = "RECEIVED", direction = Direction.OUTGOING)
	private Memory memory;
	
}
