package com.memories.app.model;

import org.springframework.data.neo4j.core.schema.Node;

import com.memories.app.model.GenericEnum.MediaType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Node
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Media extends GenericEntity {
	private String path;
	private MediaType type;
	private Memory memory;
	
	public String getMediaName() {
		String[] splits = getPath().split("s3.amazonaws.com/");
		return splits[splits.length - 1];
	}
}
