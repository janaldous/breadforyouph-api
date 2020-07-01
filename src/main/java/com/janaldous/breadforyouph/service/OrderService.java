package com.janaldous.breadforyouph.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.janaldous.breadforyouph.data.AddressRepository;
import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.data.DeliveryDateRepository;
import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.OrderItem;
import com.janaldous.breadforyouph.data.OrderRepository;
import com.janaldous.breadforyouph.data.OrderStatus;
import com.janaldous.breadforyouph.data.OrderTracking;
import com.janaldous.breadforyouph.data.OrderTrackingRepository;
import com.janaldous.breadforyouph.data.Product;
import com.janaldous.breadforyouph.data.ProductRepository;
import com.janaldous.breadforyouph.data.UserRepository;
import com.janaldous.breadforyouph.domain.mapper.OrderConfirmationMapper;
import com.janaldous.breadforyouph.domain.mapper.OrderItemMapper;
import com.janaldous.breadforyouph.domain.mapper.OrderMapper;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;
import com.janaldous.breadforyouph.webfacade.dto.OrderUpdateDto;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private OrderTrackingRepository orderTrackingRepository;
	
	@Autowired
	private DeliveryDateRepository deliveryDateRepository;

	public OrderConfirmation order(OrderDto orderDto) {
		OrderDetail orderDetail = OrderMapper.toEntity(orderDto);
		orderDetail.setOrderDate(new Date());

		// save transitive entities
		userRepository.save(orderDetail.getUser());
		addressRepository.save(orderDetail.getShipping());

		// set product
		Product originalBananaBread = productRepository.findByName("Original Banana Bread");
		OrderItem orderItem = OrderItemMapper.toEntity(orderDto.getQuantity(), originalBananaBread, orderDetail);
		orderDetail.setOrderItems(Arrays.asList(orderItem));
		
		// set delivery date
		DeliveryDate deliveryDate = deliveryDateRepository.findByDate(orderDto.getDeliveryDate());
		orderDetail.setDeliveryDate(deliveryDate);

		// set sum
		BigDecimal total = orderDetail.getOrderItems().stream().map(x -> x.getBuyingPrice()).reduce(BigDecimal.ZERO,
				(subtotal, element) -> subtotal.add(element));
		orderDetail.setTotal(total);
		
		// set tracking
		OrderTracking tracking = new OrderTracking();
		tracking.setStatus(OrderStatus.REGISTERED);
		orderTrackingRepository.save(tracking);
		orderDetail.setTracking(tracking);

		OrderDetail savedOrder = orderRepository.save(orderDetail);
		
		return OrderConfirmationMapper.toDto(savedOrder);
	}

	public List<OrderDetail> getOrders(Optional<OrderStatus> status) {
		if (!status.isPresent()) {
			return orderRepository.findAll(Sort.by("orderDate"));
		}
		return orderRepository.findAllByStatus(status.get(), Sort.by("orderDate"));
	}

	public OrderDetail updateOrder(Long id, @Valid OrderUpdateDto orderDto) {
		Optional<OrderDetail> optOrder = orderRepository.findById(id);
		if (!optOrder.isPresent()) throw new ResourceNotFoundException("Order with id: " + id + " was not found");
		
		OrderDetail orderDetail = optOrder.get();
		orderDetail.getTracking().setStatus(orderDto.getStatus());
		
		return orderRepository.save(orderDetail);
	}

	public OrderDetail getOrder(Long id) {
		if (id == null) throw new IllegalArgumentException();
		
		Optional<OrderDetail> optOrder = orderRepository.findById(id);
		if (!optOrder.isPresent()) throw new ResourceNotFoundException("Order with id: " + id + " was not found");
		
		return optOrder.get();
	}

}
