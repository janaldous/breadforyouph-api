package com.janaldous.breadforyouph.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.janaldous.breadforyouph.service.OrderService;
import com.janaldous.breadforyouph.testutil.OrderDtoMockFactory;
import com.janaldous.breadforyouph.testutil.TestUtils;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;

@SpringBootTest
class DeliveryDateRepositoryIT {

	@Autowired
	private DeliveryDateRepository deliveryDateRepository;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@AfterEach
	public void afterEach() {
		assertEquals(0, orderRepository.count());
		assertEquals(0, deliveryDateRepository.count());
	}

	@Test
	void getAvailableDeliveryDateOnlyGivenSomeFilledDates() {
		// create delivery dates
		assertEquals(0, deliveryDateRepository.count());

		for (int i = 0; i < 3; i++) {
			DeliveryDate deliveryDate = new DeliveryDate();
			deliveryDate.setDate(TestUtils.convertLocalDateToDate(LocalDate.now().plusDays(i)));
			deliveryDate.setOrderLimit(2);
			deliveryDateRepository.save(deliveryDate);
		}

		assertEquals(3, deliveryDateRepository.count());

		OrderDto orderDto = OrderDtoMockFactory.factory();
		orderDto.setDeliveryDateId(deliveryDateRepository.findByDate(TestUtils.convertLocalDateToDate(LocalDate.now())).getId());
		
		orderService.order(orderDto);
		orderService.order(orderDto);
		
		// set up fill orders in same date
		Page<DeliveryDate> result = deliveryDateRepository.findDeliveryDates(PageRequest.of(0, 3));
		assertEquals(2, result.getTotalElements());
		assertEquals(2, result.getContent().size());
		
		orderRepository.deleteAll();
		deliveryDateRepository.deleteAll();
	}

}
