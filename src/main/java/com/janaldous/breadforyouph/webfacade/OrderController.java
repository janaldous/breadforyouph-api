package com.janaldous.breadforyouph.webfacade;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.OrderStatus;
import com.janaldous.breadforyouph.service.OrderService;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;
import com.janaldous.breadforyouph.webfacade.dto.OrderUpdateDto;

import io.swagger.annotations.Api;

@Api
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/order")
	public @ResponseBody ResponseEntity<OrderConfirmation> order(@Valid @RequestBody OrderDto orderDto) {
		return new ResponseEntity<OrderConfirmation>(orderService.order(orderDto), HttpStatus.CREATED);
	}

	@GetMapping("/order")
	public @ResponseBody List<OrderDetail> getOrders(Optional<OrderStatus> status) {
		return orderService.getOrders(status);
	}
	
	@GetMapping("/order/{id}")
	public @ResponseBody OrderDetail getOrder(@PathVariable(value = "id") String idStr) {
		Long id = Long.valueOf(idStr);
		return orderService.getOrder(id);
	}

	@PutMapping("/order/{id}")
	public @ResponseBody ResponseEntity<OrderDetail> updateOrder(@PathVariable(value = "id") String idStr,
			@Valid @RequestBody OrderUpdateDto orderDto) {
		Long id = Long.valueOf(idStr);
		return new ResponseEntity<OrderDetail>(orderService.updateOrder(id, orderDto), HttpStatus.ACCEPTED);
	}

}
