package com.memories.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;


public interface EmailSenderService {
	
	public void sendEmail(String toEmail,
			String subject,
			Map<String, Object> model,
			String template
			);
	
}
