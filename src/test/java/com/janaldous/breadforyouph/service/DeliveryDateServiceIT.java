package com.janaldous.breadforyouph.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.data.DeliveryDateRepository;
import com.janaldous.breadforyouph.testutil.TestUtils;
import com.janaldous.breadforyouph.webfacade.dto.DeliveryDateDto;

@SpringBootTest
class DeliveryDateServiceIT {

	@Autowired
	private DeliveryDateRepository repository;
	
	@Autowired
	private DeliveryDateService service;
	
	@BeforeEach
	public void beforeEach() {
		assertEquals(0, repository.count());
	}
	
	@AfterEach
	public void afterEach() {
		repository.deleteAll();
	}
	
	@Test
	void smokeTest() {
		DeliveryDate date1 = new DeliveryDate();
		date1.setDate(new Date(TestUtils.getTimeAsMilis(1)));
		repository.save(date1);
		
		assertEquals(1, repository.count());
		assertEquals(6, repository.findAll().get(0).getOrderLimit());
		
		Page<DeliveryDate> result = service.getDeliveryDates(0, 5);
		assertEquals(1, result.getTotalElements());
		assertEquals(1, result.getContent().size());
	}

	@Test
	void testOnePage() {
		for (int i = 0; i < 5;  i++) {
			DeliveryDate date = new DeliveryDate();
			date.setDate(new Date(TestUtils.getTimeAsMilis(i)));
			repository.save(date);
		}
		
		assertEquals(5, repository.count());
		
		Page<DeliveryDate> result = service.getDeliveryDates(0, 5);
		assertEquals(5, result.getContent().size());
		assertEquals(1, result.getTotalPages());
	}
	
	@Test
	void testRepoMoreThanOnePageThenReturn2Pages() {
		for (int i = 0; i < 8;  i++) {
			DeliveryDate date = new DeliveryDate();
			date.setDate(new Date(TestUtils.getTimeAsMilis(i)));
			repository.save(date);
		}
		
		assertEquals(8, repository.count());
		
		Page<DeliveryDate> result = service.getDeliveryDates(0, 5);
		assertEquals(5, result.getContent().size());
		assertEquals(2, result.getTotalPages());
	}
	
	@Test
	void testSaveDeliveryDate() {
		DeliveryDateDto input = new DeliveryDateDto();
		input.setDate(new Date(LocalDate.now().plusDays(1).toEpochDay()));
		
		DeliveryDate result = service.createDeliveryDate(input);
		
		assertNotNull(result);
		assertNotNull(result.getId());
	}

}
