package com.janaldous.breadforyouph.webfacade.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDto {

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@MobileNumberConstraint
	private String contactNumber;
	@Email
	private String email;
	
}
