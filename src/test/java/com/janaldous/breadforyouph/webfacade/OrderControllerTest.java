package com.janaldous.breadforyouph.webfacade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.janaldous.breadforyouph.service.OrderService;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;

class OrderControllerTest {

	@Mock
	private OrderService orderService;
	
	@InjectMocks
	private OrderController orderController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test()
	void testValidation() {
		OrderDto order = new OrderDto();
		orderController.order(order);
	}

}
