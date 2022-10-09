package com.memories.app.model;

import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Node
@Data
@EqualsAndHashCode(callSuper = false)
public class Location extends GenericEntity{

	
	private double longitude;
	private double latitude;
	
	
}
