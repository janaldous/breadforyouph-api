package com.janaldous.breadforyouph.webfacade.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.janaldous.breadforyouph.service.DeliveryDateService;
import com.janaldous.breadforyouph.service.OrderService;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;

public class PublicControllerTest {

	@Mock
	private OrderService orderService;
	
	@Mock
	private DeliveryDateService deliveryDateService;

	@InjectMocks
	private PublicController publicController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void testOrderValidation() {
		OrderDto order = new OrderDto();
		publicController.order(order);
	}

	
}
