package com.janaldous.breadforyouph.domain.mapper;

import com.janaldous.breadforyouph.data.User;
import com.janaldous.breadforyouph.webfacade.dto.UserDto;

public class UserMapper {

	public static User toEntity(UserDto userDto) {
		if (userDto == null) return null;
		
		User output = new User();
		output.setContactNumber(userDto.getContactNumber());
		output.setEmail(userDto.getEmail());
		output.setFirstName(userDto.getFirstName());
		output.setLastName(userDto.getLastName());
		return output;
	}

	public static UserDto toDto(User input) {
		if (input == null) throw new IllegalArgumentException();
		
		UserDto output = new UserDto();
		output.setContactNumber(input.getContactNumber());
		output.setEmail(input.getEmail());
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		
		return output;
	}
	
}
