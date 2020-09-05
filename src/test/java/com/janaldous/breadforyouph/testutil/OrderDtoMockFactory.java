package com.janaldous.breadforyouph.testutil;

import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.webfacade.dto.AddressDto;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;
import com.janaldous.breadforyouph.webfacade.dto.UserDto;

public class OrderDtoMockFactory {
	
	public static OrderDto factory() {
		return getMockOrder();
	}
	
	private static OrderDto getMockOrder() {
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
		orderMock.setProducts(ProductDtoMockFactory.getMockProducts());
		return orderMock;
	}

}
