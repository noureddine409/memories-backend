package com.memories.app.model;

import java.util.List;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import com.memories.app.model.GenericEnum.Activities;
import com.memories.app.model.GenericEnum.Audience;
import com.memories.app.model.GenericEnum.Feeling;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Node
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Memory extends GenericEntity {
	private String body;
	private boolean isFavorite;
	private Audience audience;
	@Relationship(type = "IMPORT", direction = Direction.OUTGOING)
	private List<Media> medias;
	@Relationship(type = "LOCATED_AT", direction = Direction.OUTGOING)
	private Location location;
	private Feeling feeling;
	private Activities activity;
	@Relationship(type = "CREATE", direction = Direction.INCOMING)
	private User createdBy;
	@Relationship(type = "MENSIONED_IN", direction = Direction.OUTGOING)
	private List<User> mensioned;
	
	@Relationship(type = "RECEIVED", direction = Direction.INCOMING)
	private List<Comment> comments;
	
	@Relationship(type = "LOVES", direction = Direction.INCOMING)
	private List<User> lovers;
	

}
