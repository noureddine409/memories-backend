package com.memories.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.memories.app.service.EmailSenderService;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
final Logger LOG = LoggerFactory.getLogger(GenericServiceImpl.class);
	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Override
	public void sendEmail(String toEmail,
			String subject,
			String body
			) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		mailSender.send(message);
		
		LOG.info("Mail send successfully...");
		
	}
}
