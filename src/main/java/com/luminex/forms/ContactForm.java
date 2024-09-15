package com.luminex.forms;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String description;
	private boolean favorite=false;
	private String facebookLink;
	private String instaLink;
	private MultipartFile profileimage;
	
}
