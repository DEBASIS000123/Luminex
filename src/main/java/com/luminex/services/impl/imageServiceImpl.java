package com.luminex.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.luminex.helpers.AppConstants;
import com.luminex.services.imageService;

@Service
public class imageServiceImpl implements imageService{
	
	private Cloudinary cloudnary;
	
	public imageServiceImpl(Cloudinary cloudinary) {
		this.cloudnary=cloudinary;
	}

	@Override
	public String uploadImage(MultipartFile profileimage, String filename){
		
		//String filename=UUID.randomUUID().toString();
		
		try {
			byte[] data=new byte[profileimage.getInputStream().available()];
			
			profileimage.getInputStream().read(data);
			cloudnary.uploader().upload(data, ObjectUtils.asMap("public_id",filename));
			
			return this.getUrlFromPublicId(filename);
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUrlFromPublicId(String publicId) {
		// TODO Auto-generated method stub
		return cloudnary.url().transformation(new Transformation<>().width(AppConstants.CONTACT_IMAGE_WIDTH).height(AppConstants.CONTACT_IMAGE_WIDTH).crop(AppConstants.CONTACT_IMAGE_CROP)).generate(publicId);
	}
	
	

}
