package com.janaldous.breadforyouph.webfacade.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddressDto {

	@NotNull
	private String line1;
	@NotNull
	private String village;
	@NotNull
	private String city;
	@NotNull
	private String province;
	@NotNull
	private String postcode;

}
