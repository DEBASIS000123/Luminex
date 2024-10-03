package com.luminex.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.luminex.services.EmailService;

@Service
public class emailServiceimpl implements EmailService{
	
	@Autowired
	private JavaMailSender emailsender;

	@Value("${spring.mail.properties.domain_name}")
	private String domain_name;
	

	@Override
	public void sendEmailWithHtml() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEmailWithAttachment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEmail(String to, String subject, String body) {
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		message.setFrom(domain_name);
		emailsender.send(message);
	}

}
