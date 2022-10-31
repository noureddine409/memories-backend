package com.memories.app.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.memories.app.service.EmailSenderService;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
final Logger LOG = LoggerFactory.getLogger(GenericServiceImpl.class);
	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Override
	public void sendEmail(String toEmail,
			String subject,
			Map<String, Object> model,
			String template
			) {
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariables(model);
			String html = templateEngine.process(template, context);
			helper.setTo(toEmail);
			helper.setFrom(fromEmail);
			helper.setSubject(subject);
			helper.setText(html, true);
			mailSender.send(mimeMessage);
		}
		catch(MessagingException e) {
			e.printStackTrace();
		}
		
		LOG.info("Mail send successfully...");
		
	}
	
	
	
}
