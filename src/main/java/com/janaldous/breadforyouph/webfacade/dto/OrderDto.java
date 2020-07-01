package com.janaldous.breadforyouph.webfacade.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.PaymentType;

import lombok.Data;

@Data
@ValidAddressConstraint
public class OrderDto {

	@NotNull
	@Valid
	private UserDto user;
	
	@NotNull
	private Long quantity;
	
	@NotNull
	private PaymentType paymentType;
	
	@NotNull
	private DeliveryType deliveryType;
	
	@NotNull
	private Date deliveryDate;

	@NotNull
	private AddressDto address;
	
}
