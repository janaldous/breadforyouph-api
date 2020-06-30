package com.janaldous.breadforyouph.webfacade.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DeliveryDateDto {

	private Date date;
	private int orderLimit;
	
}
