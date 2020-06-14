package com.janaldous.breadforyouph.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.janaldous.breadforyouph.data.AddressRepository;
import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.OrderItemRepository;
import com.janaldous.breadforyouph.data.OrderRepository;
import com.janaldous.breadforyouph.data.OrderStatus;
import com.janaldous.breadforyouph.data.OrderTracking;
import com.janaldous.breadforyouph.data.OrderTrackingRepository;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.data.Product;
import com.janaldous.breadforyouph.data.ProductRepository;
import com.janaldous.breadforyouph.data.User;
import com.janaldous.breadforyouph.data.UserRepository;
import com.janaldous.breadforyouph.testutil.TestUtils;
import com.janaldous.breadforyouph.webfacade.dto.AddressDto;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;
import com.janaldous.breadforyouph.webfacade.dto.UserDto;

@SpringBootTest
class OrderServiceIT {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	private static boolean isDBInitialized = false;

	@Autowired
	private OrderTrackingRepository orderTrackingRepository;

	@BeforeEach
	public void beforeEach() {
		if (!isDBInitialized) {
			initializeDB();
			isDBInitialized = true;
		}
		
		Product product = new Product();
		product.setCode("code");
		product.setDescription("The only product we have");
		product.setName("Original Banana Bread");

		if (productRepository.findByName("Original Banana Bread") == null) {
			product.setUnitPrice(new BigDecimal("165"));
			productRepository.save(product);
		}

		Product origBananaBread = productRepository.findByName("Original Banana Bread");
		assertThat(origBananaBread, is(not(nullValue())));
	}
	
	private void initializeDB() {
		for (int i = 0; i < 10; i++) {
			OrderDetail mockOrder = mockOrderDetail();
			if (i % 2 == 1) mockOrder.getTracking().setStatus(OrderStatus.DELIVERED);
			else mockOrder.getTracking().setStatus(OrderStatus.REGISTERED);
			
			// random date between 90 days ago and now
			long timeStart = new Date().getTime() - TimeUnit.DAYS.toMillis(1) * 90;
			mockOrder.setOrderDate(TestUtils.between(new Date(timeStart), new Date()));
			
			userRepository.save(mockOrder.getUser());
			orderTrackingRepository.save(mockOrder.getTracking());
			orderRepository.save(mockOrder);
		}
		assertEquals(10, orderRepository.count());
	}

	@Test
	void testCreateOrder() {
		OrderDto input = getMockOrder();
		OrderConfirmation order = orderService.order(input);

		assertEquals(11, orderRepository.count());
		assertEquals(11, userRepository.count());
		assertEquals(1, addressRepository.count());
		assertEquals(1, orderItemRepository.count());
		assertEquals(11, orderTrackingRepository.count());
		
		// clean up
		orderRepository.deleteById(order.getOrderNumber());
		assertEquals(10, orderRepository.count());
	}
	
	@Test
	void testGetOrdersSortedByOrderDate() {
		List<OrderDetail> orders = orderService.getOrders(Optional.empty());
		
		assertEquals(10, orders.size());
		for (int i = 0; i < 9; i++) {
			assertTrue(orders.get(i).getOrderDate().before(orders.get(i+1).getOrderDate()));
		}
	}
	
	@Test
	void testGetOrdersFilterByOrderStatus() {
		List<OrderDetail> orders = orderService.getOrders(Optional.of(OrderStatus.REGISTERED));
		
		assertEquals(5, orders.size());
		for (int i = 0; i < 4; i++) {
			assertTrue(orders.get(i).getOrderDate().before(orders.get(i+1).getOrderDate()));
			assertEquals(OrderStatus.REGISTERED, orders.get(i).getTracking().getStatus());
		}
	}
	
	private static OrderDetail mockOrderDetail() {
		OrderDetail mockSavedOrder = new OrderDetail();
		User userEntity = new User();
		userEntity.setFirstName("Mickey");
		userEntity.setLastName("Mouse");
		userEntity.setContactNumber("01234");
		mockSavedOrder.setUser(userEntity);
		OrderTracking tracking = new OrderTracking();
		tracking.setStatus(OrderStatus.REGISTERED);
		mockSavedOrder.setTracking(tracking);
		return mockSavedOrder;
	}

	private OrderDto getMockOrder() {
		OrderDto orderMock = new OrderDto();
		AddressDto address = new AddressDto();
		address.setLine1("Main Street");
		address.setVillage("Mickey Mouse Clubhouse");
		address.setCity("Sta Rosa");
		address.setProvince("Murica");
		address.setPostcode("4026");
		orderMock.setAddress(address);
		orderMock.setDeliveryType(DeliveryType.DELIVER);
		UserDto user = new UserDto();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setContactNumber("1234567890");
		orderMock.setUser(user);
		orderMock.setPaymentType(PaymentType.CASH);
		orderMock.setQuantity(1l);
		return orderMock;
	}

}
