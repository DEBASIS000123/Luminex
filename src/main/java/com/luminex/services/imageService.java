package com.luminex.services;

import org.springframework.web.multipart.MultipartFile;

public interface imageService {

	String uploadImage(MultipartFile profileimage,String filename);
	
	String getUrlFromPublicId(String publicId);
}
