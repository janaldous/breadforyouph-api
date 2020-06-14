package com.janaldous.breadforyouph.domain.mapper;

import com.janaldous.breadforyouph.data.Address;
import com.janaldous.breadforyouph.webfacade.dto.AddressDto;

public class AddressMapper {

	public static Address toEntity(AddressDto input) {
		if (input == null) return null;
		
		Address output = new Address();
		
		output.setAddressLineOne(input.getLine1());
		output.setAddressLineTwo(input.getVillage());
		output.setCity(input.getCity());
		output.setPostalCode(input.getPostcode());
		output.setProvince(input.getProvince());
		output.setCountry("Philippines");
		
		return output; 
	}
	
}
