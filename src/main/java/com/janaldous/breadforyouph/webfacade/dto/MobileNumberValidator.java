package com.janaldous.breadforyouph.webfacade.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileNumberValidator implements ConstraintValidator<MobileNumberConstraint, String> {

	public void initialize(MobileNumberValidator contactNumber) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return contactField != null && contactField.matches("^09[0-9]{9}$") && (contactField.length() == 11);
	}

}