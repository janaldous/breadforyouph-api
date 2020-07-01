package com.janaldous.breadforyouph.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.OrderStatus;
import com.janaldous.breadforyouph.data.OrderTracking;
import com.janaldous.breadforyouph.data.User;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;

class OrderConfirmationMapperTest {

	@Test
	void test() {
		OrderDetail input = getMockOrderDto();
		
		OrderConfirmation result = OrderConfirmationMapper.toDto(input);
		
		assertEquals(1234l, result.getOrderNumber());
		assertNotNull(result.getUser());
		assertEquals(input.getUser().getContactNumber(), result.getUser().getContactNumber());
		assertEquals(OrderStatus.REGISTERED, result.getOrderStatus());
		assertEquals(input.getDeliveryDate().getDate(), result.getDeliveryDate());
	}
	
	private OrderDetail getMockOrderDto() {
		OrderDetail input = new OrderDetail();
		input.setId(1234l);
		User user = new User();
		user.setContactNumber("0123456789");
		user.setEmail("example@example.com");
		user.setFirstName("example");
		user.setLastName("doe");
		user.setRole("customer");
		OrderTracking tracking = new OrderTracking();
		tracking.setStatus(OrderStatus.REGISTERED);
		input.setTracking(tracking);
		input.setUser(user);
		DeliveryDate deliveryDate = new DeliveryDate();
		deliveryDate.setDate(new Date());
		deliveryDate.setId(1123l);
		deliveryDate.setOrderLimit(6);
		input.setDeliveryDate(deliveryDate );
		
		return input;
	}

	@Test
	void testNullInput() {
		assertThrows(IllegalArgumentException.class, () -> {
			OrderConfirmationMapper.toDto(null);
		});
	}
	
	@Test
	void testNullOrderTracking() {
		OrderDetail input = getMockOrderDto();
		input.setTracking(null);
		
		assertThrows(IllegalArgumentException.class, () -> {
			OrderConfirmationMapper.toDto(input);
		});
	}

}
