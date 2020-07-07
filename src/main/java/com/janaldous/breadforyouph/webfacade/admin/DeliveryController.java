package com.janaldous.breadforyouph.webfacade.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.service.DeliveryDateService;
import com.janaldous.breadforyouph.webfacade.dto.DeliveryDateDto;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/admin")
public class DeliveryController {

	@Autowired
	private DeliveryDateService deliveryService;

	@PostMapping("/delivery")
	public ResponseEntity<DeliveryDate> createDeliveryDate(@RequestBody DeliveryDateDto deliveryDate) {
		return new ResponseEntity<DeliveryDate>(deliveryService.createDeliveryDate(deliveryDate), HttpStatus.CREATED);
	}
}
