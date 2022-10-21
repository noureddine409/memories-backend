package com.memories.app.model;

import org.springframework.data.neo4j.core.schema.Node;

import com.memories.app.model.GenericEnum.RoleName;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Node
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role extends GenericEntity {
    private RoleName name;

    @Builder
    public Role(final Long id, final RoleName name) {
        super(id);
        this.name = name;
    }
}
