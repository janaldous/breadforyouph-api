package com.janaldous.breadforyouph.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.data.DeliveryDateRepository;
import com.janaldous.breadforyouph.domain.mapper.DeliveryDateMapper;
import com.janaldous.breadforyouph.webfacade.dto.DeliveryDateDto;

@Service
public class DeliveryDateService {

	@Autowired
	private DeliveryDateRepository deliveryDateRepository;
	
	public Page<DeliveryDate> getDeliveryDates(int page, int size) {
		return deliveryDateRepository.findDeliveryDates(PageRequest.of(page, size));
	}

	public DeliveryDate createDeliveryDate(DeliveryDateDto deliveryDate) {
		DeliveryDate input = DeliveryDateMapper.toEntity(deliveryDate);
		return deliveryDateRepository.save(input);
	}
	
}
