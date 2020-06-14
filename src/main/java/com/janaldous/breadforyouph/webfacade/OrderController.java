package com.janaldous.breadforyouph.webfacade;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.service.OrderService;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	public @ResponseBody ResponseEntity<OrderDetail> order(@Valid @RequestBody OrderDto orderDto) {
		return new ResponseEntity<OrderDetail>(orderService.order(orderDto), HttpStatus.CREATED);
	}

}
