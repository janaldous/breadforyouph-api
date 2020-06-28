package com.janaldous.breadforyouph.webfacade.dto;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.testutil.ValidationTestUtils;

class ValidAddressValidatorTest {

	@Test
	void testInvalidDeliveryOrderDto() {
		OrderDto input = getMock();
		input.getAddress().setLine1(null);

	    assertThrows(ConstraintViolationException.class, () -> {
	    	ValidationTestUtils.validate(input);
	    });
	}
	
	@Test
	void testInvalidMeetupOrderDto() {
		OrderDto input = getMock();
		input.getAddress().setLine1(null);
		input.getAddress().setVillage(null);
		input.getAddress().setCity(null);
		input.getAddress().setProvince(null);
		input.getAddress().setPostcode(null);
		input.getAddress().setSpecialInstructions("");

	    assertThrows(ConstraintViolationException.class, () -> {
	    	ValidationTestUtils.validate(input);
	    });
	}
	
	@Test
	void testValidMeetupOrderDto() {
		OrderDto input = getMock();
		input.getAddress().setLine1(null);
		input.getAddress().setVillage(null);
		input.getAddress().setCity(null);
		input.getAddress().setProvince(null);
		input.getAddress().setPostcode(null);
		input.getAddress().setSpecialInstructions("My special instructions");

	    assertThrows(ConstraintViolationException.class, () -> {
	    	ValidationTestUtils.validate(input);
	    });
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
