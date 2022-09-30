package com.memories.app.model;

import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Node
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Adress extends GenericEntity{
	private String country;
	private String city;
	private int postalCode;

}
