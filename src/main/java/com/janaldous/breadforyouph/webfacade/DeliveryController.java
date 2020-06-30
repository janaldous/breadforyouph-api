package com.janaldous.breadforyouph.webfacade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.service.DeliveryDateService;
import com.janaldous.breadforyouph.service.ResourceNotFoundException;
import com.janaldous.breadforyouph.webfacade.dto.DeliveryDateDto;

import io.swagger.annotations.Api;

@Api
@RestController
public class DeliveryController {

	@Autowired
	private DeliveryDateService deliveryService;

	@GetMapping("/delivery")
	public @ResponseBody List<DeliveryDate> getDeliveryDates(@RequestParam("page") int page,
			@RequestParam("size") int size) {

		Page<DeliveryDate> resultPage = deliveryService.getDeliveryDates(page, size);
		if (page >= resultPage.getTotalPages()) {
			throw new ResourceNotFoundException(
					"Cannot find delivery dates with params: page=" + page + " size=" + size);
		}
		
		return resultPage.getContent();
	}
	
	@PostMapping("/delivery")
	public ResponseEntity<DeliveryDate> createDeliveryDate(@RequestBody DeliveryDateDto deliveryDate) {
		return new ResponseEntity<DeliveryDate>(deliveryService.createDeliveryDate(deliveryDate), HttpStatus.CREATED);
	}
}
