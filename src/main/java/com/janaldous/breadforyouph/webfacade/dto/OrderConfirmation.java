package com.janaldous.breadforyouph.webfacade.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.janaldous.breadforyouph.data.OrderStatus;

import lombok.Data;

@Data
public class OrderConfirmation {

	@NotNull
	private UserDto user;
	
	@NotNull
	private Long orderNumber;
	
	@NotNull
	private OrderStatus orderStatus;
	
	@NotNull
	private Date deliveryDate;
	
}
