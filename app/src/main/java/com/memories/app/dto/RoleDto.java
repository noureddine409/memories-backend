package com.memories.app.dto;


import java.time.LocalDateTime;

import com.memories.app.model.GenericEnum.RoleName;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleDto extends GenericDto {

    private RoleName name;

    @Builder
    public RoleDto(final Long id, final RoleName name,
                   final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.name = name;
    }
}