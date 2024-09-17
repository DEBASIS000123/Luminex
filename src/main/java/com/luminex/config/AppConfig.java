package com.luminex.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {
    
    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    
    @Value("${cloudinary.api.key}")
    private String apiKey;
    
    @Value("${cloudinary.api.secret}")
    private String apiSecret;  // Corrected from "api.screct" to "api.secret"

    @Bean
    Cloudinary cloudinary() {  // Corrected method name from "cloudnary" to "cloudinary"
        return new Cloudinary(
            ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret  // Corrected from "api_secrect" to "api_secret"
            )
        );
    }

}
