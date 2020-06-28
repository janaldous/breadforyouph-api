package com.janaldous.breadforyouph.webfacade.dto;

import lombok.Data;

@Data
public class AddressDto {

	private String line1;
	private String village;
	private String city;
	private String province;
	private String postcode;
	private String specialInstructions;

}
