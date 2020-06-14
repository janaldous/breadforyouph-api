package com.janaldous.breadforyouph.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.User;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;

class OrderConfirmationMapperTest {

	@Test
	void test() {
		OrderDetail input = new OrderDetail();
		input.setId(1234l);
		User user = new User();
		user.setContactNumber("0123456789");
		user.setEmail("example@example.com");
		user.setFirstName("example");
		user.setLastName("doe");
		user.setRole("customer");
		
		input.setUser(user);
		
		OrderConfirmation result = OrderConfirmationMapper.toDto(input);
		
		assertEquals(null, result.getDeliveryStatus());
		assertEquals(1234l, result.getOrderNumber());
		assertNotNull(result.getUser());
		assertEquals(user.getContactNumber(), result.getUser().getContactNumber());
	}
	
	@Test
	void testNullInput() {
		assertThrows(IllegalArgumentException.class, () -> {
			OrderConfirmationMapper.toDto(null);
		});
	}

}
