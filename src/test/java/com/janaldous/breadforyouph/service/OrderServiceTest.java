package com.janaldous.breadforyouph.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.janaldous.breadforyouph.data.AddressRepository;
import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.OrderRepository;
import com.janaldous.breadforyouph.data.OrderStatus;
import com.janaldous.breadforyouph.data.OrderTracking;
import com.janaldous.breadforyouph.data.OrderTrackingRepository;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.data.Product;
import com.janaldous.breadforyouph.data.ProductRepository;
import com.janaldous.breadforyouph.data.User;
import com.janaldous.breadforyouph.data.UserRepository;
import com.janaldous.breadforyouph.webfacade.dto.AddressDto;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;
import com.janaldous.breadforyouph.webfacade.dto.UserDto;

class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private AddressRepository addressRepository;
	
	@Mock
	private OrderTrackingRepository orderTrackingRepository;

	@InjectMocks
	private OrderService orderService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void test() {
		OrderDto orderDto = new OrderDto();
		AddressDto address = new AddressDto();
		address.setLine1("Main Street");
		orderDto.setAddress(address);
		orderDto.setDeliveryType(DeliveryType.DELIVER);
		orderDto.setPaymentType(PaymentType.CASH);
		orderDto.setQuantity(1l);
		UserDto user = new UserDto();
		user.setContactNumber("1234567890");
		orderDto.setUser(user);

		Product mockBananaBread = Mockito.mock(Product.class);
		Mockito.when(mockBananaBread.getUnitPrice()).thenReturn(BigDecimal.valueOf(165));
		Mockito.when(productRepository.findByName("Original Banana Bread")).thenReturn(mockBananaBread);

		OrderDetail mockSavedOrder = new OrderDetail();
		mockSavedOrder.setId(1234l);
		User userEntity = new User();
		userEntity.setContactNumber("01234");
		mockSavedOrder.setUser(userEntity);
		OrderTracking tracking = new OrderTracking();
		tracking.setStatus(OrderStatus.REGISTERED);
		mockSavedOrder.setTracking(tracking);
		
		Mockito.when(orderRepository.save(Mockito.any(OrderDetail.class))).thenReturn(mockSavedOrder);

		OrderConfirmation result = orderService.order(orderDto);

		ArgumentCaptor<OrderDetail> arg = ArgumentCaptor.forClass(OrderDetail.class);
		Mockito.verify(orderRepository).save(arg.capture());

		OrderDetail resultOrder = arg.getValue();
		assertEquals(DeliveryType.DELIVER, resultOrder.getDeliveryType());
		assertEquals(PaymentType.CASH, resultOrder.getPaymentType());
		assertNotNull(resultOrder.getOrderDate());
		assertEquals(1, resultOrder.getOrderItems().size());

		assertEquals(mockBananaBread, resultOrder.getOrderItems().get(0).getProduct());
		assertEquals(user.getContactNumber(), resultOrder.getUser().getContactNumber());
		assertEquals(address.getLine1(), resultOrder.getShipping().getAddressLineOne());
		assertEquals(new BigDecimal("165"), resultOrder.getTotal());
		
		assertEquals(OrderStatus.REGISTERED, result.getOrderStatus());
		assertNotNull(result.getOrderNumber());
	}

}
