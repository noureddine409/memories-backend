package com.memories.app.dto;



import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtToken {
    private String token;
    private final String tokenType = "Bearer";
    private Instant createdAt;
    private Instant expiresIn;
}