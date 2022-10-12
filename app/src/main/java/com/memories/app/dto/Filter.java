package com.memories.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Filter {
	private String filterName;
    private Object appliedValue;
}
