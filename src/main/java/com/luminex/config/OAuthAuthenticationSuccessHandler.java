package com.luminex.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.luminex.entities.Providers;
import com.luminex.entities.User;
import com.luminex.helpers.AppConstants;
import com.luminex.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	Logger logger=LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
	        throws IOException, ServletException {

	    logger.info("OAuthAuthenticationSuccessHandler triggered");

	    /*DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

	    String email = oauthUser.getAttribute("email").toString();
	    String name = oauthUser.getAttribute("name").toString();
	    String picture = oauthUser.getAttribute("picture").toString();

	    // Check if user already exists
	    User existingUser = userRepo.findByEmail(email).orElse(null);

	    if (existingUser == null) {
	        User newUser = new User();
	        newUser.setUserId(UUID.randomUUID().toString());  // Generate a random UUID for userId
	        newUser.setEmail(email);
	        newUser.setName(name);
	        newUser.setProfilePic(picture);
	        newUser.setPassword(UUID.randomUUID().toString());  // Generate a random password
	        newUser.setProvider(Providers.GOOGLE);
	        newUser.setEnebled(true);
	        newUser.setEmailVerfied(true);
	        newUser.setProviderUserId(oauthUser.getName());
	        newUser.setRoleList(List.of(AppConstants.ROLE_USER));

	        // Save the new user to the database
	        userRepo.save(newUser);
	        logger.info("New user created: " + email);
	    } else {
	        logger.info("User already exists: " + email);
	    }
*/
	    // identify the provider

        var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        //get the details of login information
        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerfied(true);
        user.setEnebled(true);
        user.setPassword("kunu");

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            // google
            // google attributes

            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            // github
            // github attributes
            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
        }

        else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {

        }

        else {
            logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
        }

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepo.save(user);
            System.out.println("user saved:" + user.getEmail());
        }



        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }
}
