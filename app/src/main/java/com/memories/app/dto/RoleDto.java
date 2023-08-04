package com.memories.app.dto;


import com.memories.app.model.GenericEnum.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleDto extends GenericDto {
    @NotNull
    private RoleName name;

    @Builder
    public RoleDto(final Long id, final RoleName name,
                   final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.name = name;
    }
}
