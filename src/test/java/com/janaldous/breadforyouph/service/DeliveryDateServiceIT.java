package com.janaldous.breadforyouph.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;

import com.janaldous.breadforyouph.data.AddressRepository;
import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.data.DeliveryDateRepository;
import com.janaldous.breadforyouph.data.OrderItemRepository;
import com.janaldous.breadforyouph.data.OrderRepository;
import com.janaldous.breadforyouph.data.UserRepository;
import com.janaldous.breadforyouph.testutil.TestUtils;
import com.janaldous.breadforyouph.webfacade.dto.DeliveryDateDto;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;

@DirtiesContext
@SpringBootTest
class DeliveryDateServiceIT {

	@Autowired
	private DeliveryDateRepository deliveryDateRepository;

	@Autowired
	private DeliveryDateService deliveryDateService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@BeforeEach
	public void beforeEach() {
		assertEquals(0, deliveryDateRepository.count());
		assertEquals(0, orderRepository.count());
	}

	@AfterEach
	public void afterEach() {
		orderRepository.deleteAll();
		userRepository.deleteAll();
		addressRepository.deleteAll();
		orderItemRepository.deleteAll();
		deliveryDateRepository.deleteAll();
	}

	@Test
	void smokeTest() {
		DeliveryDate date1 = new DeliveryDate();
		date1.setDate(new Date(TestUtils.getTimeAsMilis(1)));
		deliveryDateRepository.save(date1);

		assertEquals(1, deliveryDateRepository.count());
		assertEquals(6, deliveryDateRepository.findAll().get(0).getOrderLimit());

		Page<DeliveryDate> result = deliveryDateService.getDeliveryDates(0, 5);
		assertEquals(1, result.getTotalElements());
		assertEquals(1, result.getContent().size());
	}
	
	@Test
	void testOnePage() {
		for (int i = 0; i < 5; i++) {
			DeliveryDate date = new DeliveryDate();
			date.setDate(new Date(TestUtils.getTimeAsMilis(i)));
			deliveryDateRepository.save(date);
		}

		assertEquals(5, deliveryDateRepository.count());

		Page<DeliveryDate> result = deliveryDateService.getDeliveryDates(0, 5);
		assertEquals(5, result.getContent().size());
		assertEquals(1, result.getTotalPages());
	}

	@Test
	void testRepoMoreThanOnePageThenReturn2Pages() {
		for (int i = 0; i < 8; i++) {
			DeliveryDate date = new DeliveryDate();
			date.setDate(TestUtils.convertLocalDateToDate(LocalDate.now().plusDays(i)));
			deliveryDateRepository.save(date);
		}

		assertEquals(8, deliveryDateRepository.count());

		Page<DeliveryDate> result = deliveryDateService.getDeliveryDates(0, 5);
		assertEquals(5, result.getContent().size());
		assertEquals(2, result.getTotalPages());
	}

	@Test
	void testSaveDeliveryDate() {
		DeliveryDateDto input = new DeliveryDateDto();
		input.setDate(TestUtils.convertLocalDateToDate(LocalDate.now().plusDays(1)));

		DeliveryDate result = deliveryDateService.createDeliveryDate(input);

		assertNotNull(result);
		assertNotNull(result.getId());
	}

	@Test
	void testGetNullDeliveryDateThenThrowResourceNotFoundException() {
		assertThrows(ResourceNotFoundException.class, () -> deliveryDateService.getDeliveryDate(null));
	}
	
	@Test
	void testValidateDeliveryDateGivenDateWhenEnoughSpaceThenValidRequest() {
		DeliveryDate deliveryDate = new DeliveryDate();
		deliveryDate.setDate(TestUtils.convertLocalDateToDate(LocalDate.now()));
		deliveryDate = deliveryDateRepository.save(deliveryDate);
		
		assertTrue(deliveryDateService.isDeliveryDateAvailable(TestUtils.convertLocalDateToDate(LocalDate.now())));
		
		// clean up
		deliveryDateRepository.deleteById(deliveryDate.getId());
	}
	
	@Test
	void testValidateDeliveryDateGivenDateWhenNotEnoughSpaceThenInvalidRequest() {
		// set up
		DeliveryDate deliveryDate = new DeliveryDate();
		deliveryDate.setDate(TestUtils.convertLocalDateToDate(LocalDate.now()));
		deliveryDate.setOrderLimit(1);
		deliveryDate = deliveryDateRepository.save(deliveryDate);
		
		OrderDto orderDtoMock = OrderDtoMockFactory.factory();
		orderDtoMock.setDeliveryDate(deliveryDate.getDate());
		OrderConfirmation orderConfirmation = orderService.order(orderDtoMock);
		
		// action
		assertFalse(deliveryDateService.isDeliveryDateAvailable(orderConfirmation.getDeliveryDate()));
		
		// clean up
		orderRepository.deleteAll();
		deliveryDateRepository.deleteById(deliveryDate.getId());
	}

}
