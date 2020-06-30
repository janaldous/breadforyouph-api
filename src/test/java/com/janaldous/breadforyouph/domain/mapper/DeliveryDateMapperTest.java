package com.janaldous.breadforyouph.domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.webfacade.dto.DeliveryDateDto;

class DeliveryDateMapperTest {

	@Test
	void test() {
		DeliveryDateDto dto = new DeliveryDateDto();
		dto.setDate(new Date());
		dto.setOrderLimit(6);
		
		DeliveryDate result = DeliveryDateMapper.toEntity(dto);
		
		assertEquals(6, result.getOrderLimit());
		assertEquals(dto.getDate(), result.getDate());
	}

}
