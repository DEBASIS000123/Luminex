package com.luminex.config;

import java.io.IOException;
import java.util.logging.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.luminex.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	/*
	@Bean
	public UserDetailsService userDetailService() {
		UserDetails user1=User.withDefaultPasswordEncoder().username("admin123").password("admin123").roles("ADMIN","USER").build();
		
		UserDetails user2=User.withUsername("user123").password("password").build();
		
	var	inMemoryUserDetailsManager=new InMemoryUserDetailsManager(user1);
	return inMemoryUserDetailsManager;	
	}
	*/
	@Autowired
    private SecurityCustomUserDetailService userDetailService;


    @Autowired
    private OAuthAuthenticationSuccessHandler handler;
    
    @Autowired
    private AuthenticationFailureHandler authfailureHandeler;
	
	 @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	        // user detail service ka object:
	        daoAuthenticationProvider.setUserDetailsService(userDetailService);
	        // password encoder ka object
	        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

	        return daoAuthenticationProvider;
	    }
	 
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

	        httpSecurity.authorizeHttpRequests(authorize -> {
	            //authorize.requestMatchers("/home", "/signup", "/service").permitAll();
	        	 authorize.requestMatchers("/user/**").authenticated();
	             authorize.anyRequest().permitAll();

	        });
	        httpSecurity.formLogin(formLogin -> {

	            
	            formLogin.loginPage("/login");
	            formLogin.loginProcessingUrl("/authenticate");
	            formLogin.successForwardUrl("/user/profile");
	            //formLogin.failureForwardUrl("/login?error=true");
	            // formLogin.defaultSuccessUrl("/home");
	            formLogin.usernameParameter("email");
	            formLogin.passwordParameter("password");
	            
	            formLogin.failureHandler(authfailureHandeler);
	        });

	        
	        //After login if we want to do the customize logout 
	        httpSecurity.csrf(AbstractHttpConfigurer::disable);

	        httpSecurity.logout(logoutForm -> {
	            logoutForm.logoutUrl("/do-logout");
	            logoutForm.logoutSuccessUrl("/login?logout=true");
	            
	            
	            
	            
	            
	        });

	        

	        httpSecurity.oauth2Login(oauth -> {
	            oauth.loginPage("/login");
	            oauth.successHandler(handler);  // Use the correctly spelled handler here
	        });

	        return httpSecurity.build();

	    }


	 
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	 
	 
	 

}
