package com.janaldous.breadforyouph.domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.webfacade.dto.AddressDto;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;
import com.janaldous.breadforyouph.webfacade.dto.UserDto;

class OrderMapperTest {

	@Test
	void test() {
		OrderDto orderDto = new OrderDto();
		UserDto user = new UserDto();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setContactNumber("123456789");
		user.setEmail("john.doe@example.com");
		orderDto.setUser(user);
		AddressDto address = new AddressDto();
		orderDto.setAddress(address);
		orderDto.setDeliveryType(DeliveryType.DELIVER);
		orderDto.setPaymentType(PaymentType.CASH);
		orderDto.setQuantity(1l);

		OrderDetail result = OrderMapper.toEntity(orderDto);
		assertEquals(user.getContactNumber(), result.getUser().getContactNumber());
		assertEquals(user.getFirstName(), result.getUser().getFirstName());
		assertEquals(address.getLine1(), result.getShipping().getAddressLineOne());
		assertEquals(orderDto.getDeliveryType(), result.getDeliveryType());
		assertEquals(orderDto.getPaymentType(), result.getPaymentType());
		assertEquals(null, result.getOrderItems());
	}

	@Test
	void testNullObject() {
		assertNull(AddressMapper.toEntity(null));
	}

}
