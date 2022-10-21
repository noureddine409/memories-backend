package com.memories.app.model;

import java.time.LocalDateTime;

import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Node
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ForgetPasswordToken extends GenericEntity {
	private String token;
	private User user;
	private LocalDateTime expiryDate;
	
	public void setExpiryDate(int minutes) {
		LocalDateTime now = LocalDateTime.now();
		this.expiryDate = now.plusMinutes(minutes);
	}
	
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expiryDate);
	}
	
}
