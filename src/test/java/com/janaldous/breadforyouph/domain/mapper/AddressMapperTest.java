package com.janaldous.breadforyouph.domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.Address;
import com.janaldous.breadforyouph.webfacade.dto.AddressDto;

class AddressMapperTest {

	@Test
	void testFilledObject() {
		AddressDto input = new AddressDto();
		input.setLine1("Main Street");
		input.setVillage("Village 1");
		input.setCity("Sta Rosa");
		input.setProvince("Laguna");
		input.setPostcode("4026");
		input.setSpecialInstructions("wait for the gate to open");

		Address result = AddressMapper.toEntity(input);

		assertEquals(input.getCity(), result.getCity());
		assertEquals(input.getLine1(), result.getAddressLineOne());
		assertEquals(input.getVillage(), result.getAddressLineTwo());
		assertEquals(input.getPostcode(), result.getPostalCode());
		assertEquals(input.getProvince(), result.getProvince());
		assertEquals("Philippines", result.getCountry());
		assertEquals(input.getSpecialInstructions(), result.getSpecialInstructions());
	}

	@Test
	void testNullObject() {
		assertNull(AddressMapper.toEntity(null));
	}

}
