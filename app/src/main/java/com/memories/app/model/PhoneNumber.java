package com.memories.app.model;

import org.springframework.data.neo4j.core.schema.Node;

import com.memories.app.model.GenericEnum.Region;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Node
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PhoneNumber extends GenericEntity {
	private Region region;
	private String phoneNumber;
	
}
