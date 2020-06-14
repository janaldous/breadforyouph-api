package com.janaldous.breadforyouph.webfacade.dto;

import lombok.Data;

@Data
public class OrderConfirmation {

	private UserDto user;
	private Long orderNumber;
	private DeliveryStatus deliveryStatus;
	
}
