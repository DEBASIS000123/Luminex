package com.luminex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.luminex.services.EmailService;

@SpringBootTest
class LuminexApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService service;

	@Test
	void sendEmailTest() {
		service.sendEmail("debasismishra000123@gmail.com", "Testing mail", "Working fine");
	}

}
