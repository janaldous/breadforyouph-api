package com.janaldous.breadforyouph.domain.mapper;

import javax.validation.Valid;

import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;

public class OrderMapper {

	public static OrderDetail toEntity(@Valid OrderDto orderDto) {
		if (orderDto == null) return null;
		
		OrderDetail output = new OrderDetail();
		output.setUser(UserMapper.toEntity(orderDto.getUser()));
		output.setShipping(AddressMapper.toEntity(orderDto.getAddress()));
		output.setDeliveryType(orderDto.getDeliveryType());
		output.setPaymentType(orderDto.getPaymentType());
		
		return output;
	}

}
