package com.janaldous.breadforyouph.webfacade.dto;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.testutil.ValidationTestUtils;

class MobileNumberValidatorTest {

	private static class Person {
		@MobileNumberConstraint
		public String contactNumber;
	}
	
	@Test
	void testValidMobileNumber() {
		Person input = new Person();
		input.contactNumber = "09123456789";

		ValidationTestUtils.validate(input);
	}
	
	/**
	 * Invalid number because PH mobile number should start with 09[9 digits after]
	 */
	@Test
	void testInvalidMobileNumber() {
		Person input = new Person();
		input.contactNumber = "01123456789";

		assertThrows(ConstraintViolationException.class, () -> {
	    	ValidationTestUtils.validate(input);
	    });
	}
	
	/**
	 * Invalid number because PH mobile number should be 11 characters long
	 */
	@Test
	void testInvalidLengthMobileNumber() {
		Person input = new Person();
		input.contactNumber = "0912345678";

		assertThrows(ConstraintViolationException.class, () -> {
	    	ValidationTestUtils.validate(input);
	    });
	}
	
	@Test
	void testNullMobileNumber() {
		Person input = new Person();
		input.contactNumber = null;

		assertThrows(ConstraintViolationException.class, () -> {
	    	ValidationTestUtils.validate(input);
	    });
	}
}
