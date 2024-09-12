package com.luminex.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
	
	@NotBlank(message = "Usernmae is required")
	@Size(min =3 , message="Min 3 characters is required")
	private String name;
	
	@Email(message="Invalid Email Address")
	private String email;
	
	@NotBlank(message="Password is required")
	private String password;
	
	private String about;
	
	@Size(min = 8,max = 12, message = "Invalid Phone Number")
	private String phoneNumber;
	
}
