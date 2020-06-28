package com.janaldous.breadforyouph.testutil;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationTestUtils {

	public static void validate(Object input) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();
	    Set<ConstraintViolation<Object>> violations = validator.validate(input);
	    if (!violations.isEmpty()) {
	      throw new ConstraintViolationException(violations);
	    }
	}
	
}
