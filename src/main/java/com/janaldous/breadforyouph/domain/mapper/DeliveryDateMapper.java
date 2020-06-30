package com.janaldous.breadforyouph.domain.mapper;

import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.webfacade.dto.DeliveryDateDto;

public class DeliveryDateMapper {

	public static DeliveryDate toEntity(DeliveryDateDto input) {
		DeliveryDate output = new DeliveryDate();
		output.setDate(input.getDate());
		output.setOrderLimit(input.getOrderLimit());
		
		return output;
	}

}
