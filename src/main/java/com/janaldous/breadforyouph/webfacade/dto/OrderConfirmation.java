package com.janaldous.breadforyouph.webfacade.dto;

import com.janaldous.breadforyouph.data.OrderStatus;

import lombok.Data;

@Data
public class OrderConfirmation {

	private UserDto user;
	private Long orderNumber;
	private OrderStatus orderStatus;
	
}
