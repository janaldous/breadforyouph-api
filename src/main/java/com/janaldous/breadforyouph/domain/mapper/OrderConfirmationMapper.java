package com.janaldous.breadforyouph.domain.mapper;

import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;

public class OrderConfirmationMapper {

	public static OrderConfirmation toDto(OrderDetail input) {
		
		OrderConfirmation output = new OrderConfirmation();
		output.setOrderNumber(input.getId());
		output.setUser(UserMapper.toDto(input.getUser()));
		
		return output;
	}
	
}
