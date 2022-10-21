package com.memories.app.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {
	
	public void sendEmail(String toEmail, String subject, String body);
	
}
