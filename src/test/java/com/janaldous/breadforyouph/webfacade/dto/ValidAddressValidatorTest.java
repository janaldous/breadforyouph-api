package com.janaldous.breadforyouph.webfacade.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.testutil.ValidationTestUtils;

class ValidAddressValidatorTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testInvalidDeliveryOrderDto() {
		OrderDto input = getMock();
		input.getAddress().setLine1(null);

		Set<ConstraintViolation<OrderDto>> constraintViolations = validator.validate(input);
		assertEquals(1, constraintViolations.size());
		assertEquals("Address fields should not be null when Delivery is chosen",
				constraintViolations.iterator().next().getMessage());
	}

	@Test
	void testInvalidMeetupOrderDto() {
		OrderDto input = getMock();
		input.setDeliveryType(DeliveryType.MEET_UP);
		input.getAddress().setLine1(null);
		input.getAddress().setVillage(null);
		input.getAddress().setCity(null);
		input.getAddress().setProvince(null);
		input.getAddress().setPostcode(null);
		input.getAddress().setSpecialInstructions("");

		Set<ConstraintViolation<OrderDto>> constraintViolations = validator.validate(input);
		assertEquals(1, constraintViolations.size());
		assertEquals("Special instructions must not be null when Meet up is chosen",
				constraintViolations.iterator().next().getMessage());
	}

	@Test
	void testValidMeetupOrderDto() {
		OrderDto input = getMock();
		input.setDeliveryType(DeliveryType.MEET_UP);
		input.getAddress().setLine1(null);
		input.getAddress().setVillage(null);
		input.getAddress().setCity(null);
		input.getAddress().setProvince(null);
		input.getAddress().setPostcode(null);
		input.getAddress().setSpecialInstructions("My special instructions");

		ValidationTestUtils.validate(input);
	}

	private OrderDto getMock() {
		OrderDto orderMock = new OrderDto();
		AddressDto address = new AddressDto();
		address.setLine1("Main Street");
		address.setVillage("Mickey Mouse Clubhouse");
		address.setCity("Sta Rosa");
		address.setProvince("Murica");
		address.setPostcode("4026");
		orderMock.setAddress(address);
		orderMock.setDeliveryType(DeliveryType.DELIVER);
		orderMock.setDeliveryDate(new Date());
		UserDto user = new UserDto();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setContactNumber("09123456789");
		orderMock.setUser(user);
		orderMock.setPaymentType(PaymentType.CASH);
		orderMock.setQuantity(1l);
		return orderMock;
	}

}
