package com.janaldous.breadforyouph.webfacade.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.janaldous.breadforyouph.data.DeliveryType;

import io.micrometer.core.instrument.util.StringUtils;

public class ValidAddressValidator implements ConstraintValidator<ValidAddressConstraint, OrderDto> {

    public void initialize(ValidAddressConstraint constraintAnnotation) {

    }

	@Override
	public boolean isValid(OrderDto value, ConstraintValidatorContext context) {
		if (value.getDeliveryType() == DeliveryType.MEET_UP) {
			if (StringUtils.isBlank(value.getAddress().getSpecialInstructions())) {
				return false;
			}
		} else if (value.getAddress() != null){
			if (StringUtils.isBlank(value.getAddress().getLine1())) {
				return false;
			}
			if (StringUtils.isBlank(value.getAddress().getVillage())) {
				return false;
			}
			if (StringUtils.isBlank(value.getAddress().getCity())) {
				return false;
			}
			if (StringUtils.isBlank(value.getAddress().getProvince())) {
				return false;
			}
			if (StringUtils.isBlank(value.getAddress().getPostcode())) {
				return false;
			}
		}
		return true;
	}

}
