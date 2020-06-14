package com.janaldous.breadforyouph.domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.User;
import com.janaldous.breadforyouph.webfacade.dto.UserDto;

class UserMapperTest {

	@Test
	void testFilledObject() {
		UserDto userDto = new UserDto();
		userDto.setContactNumber("1234567890");
		userDto.setEmail("example@example.com");
		userDto.setFirstName("John");
		userDto.setLastName("Doe");

		User result = UserMapper.toEntity(userDto);

		assertEquals(userDto.getContactNumber(), result.getContactNumber());
		assertEquals(userDto.getEmail(), result.getEmail());
		assertEquals(userDto.getFirstName(), result.getFirstName());
		assertEquals(userDto.getLastName(), result.getLastName());
	}

	@Test
	void testEmptyObject() {
		assertNull(UserMapper.toEntity(null));
	}
	
	@Test
	void testToDto() {
		User user = new User();
		user.setId(1234);
		user.setContactNumber("1234567890");
		user.setEmail("example@example.com");
		user.setFirstName("example");
		user.setLastName("doe");
		user.setRole("customer");
		
		UserDto result = UserMapper.toDto(user);
		
		assertEquals(user.getContactNumber(), result.getContactNumber());
		assertEquals(user.getFirstName(), result.getFirstName());
		assertEquals(user.getEmail(), result.getEmail());
		assertEquals(user.getLastName(), result.getLastName());
	}
	
	@Test
	void testToDtoNullObject() {
		assertThrows(IllegalArgumentException.class, () -> {
			UserMapper.toDto(null);
		});
	}

}
