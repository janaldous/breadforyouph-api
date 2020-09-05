package com.janaldous.breadforyouph.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.testutil.ProductDtoMockFactory;
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
		orderDto.setProducts(ProductDtoMockFactory.getMockProducts());
		orderDto.setDeliveryDateId(0l);

		OrderDetail result = OrderMapper.toEntity(orderDto);
		assertEquals(user.getContactNumber(), result.getUser().getContactNumber());
		assertEquals(user.getFirstName(), result.getUser().getFirstName());
		assertEquals(address.getLine1(), result.getShipping().getAddressLineOne());
		assertEquals(orderDto.getDeliveryType(), result.getDeliveryType());
		assertEquals(orderDto.getPaymentType(), result.getPaymentType());
		assertEquals(null, result.getDeliveryDate());
		assertEquals(null, result.getOrderItems());
	}

	@Test
	void testNullObject() {
		assertNull(AddressMapper.toEntity(null));
	}

}
