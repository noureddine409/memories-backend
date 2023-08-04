package com.memories.app.controller;

import com.memories.app.dto.ContactUsDto;
import com.memories.app.dto.GenericDto;
import com.memories.app.model.GenericEntity;
import com.memories.app.model.User;
import com.memories.app.service.EmailSenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/contact")
public class ContactController extends GenericController<GenericEntity, GenericDto> {
	
	private final EmailSenderService emailService;
	
	@Value("${spring.mail.username}")
	private String toEmail;

	public ContactController(EmailSenderService emailService) {
		this.emailService = emailService;
	}

	@PostMapping("contactUs")
	public ResponseEntity<?> contactUs(@Valid @RequestBody ContactUsDto dto) {
		final User user = getCurrentUser();
		final String subject = dto.getSubject();
		Map<String, Object> params = new HashMap<>();
		params.put("email", toEmail);
		params.put("user", user);
		params.put("body", dto.getBody());
		params.put("subject", subject);
		params.put("signature", "sig");
		
		emailService.sendEmail(toEmail, subject, params, "contact-us.html");
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}
