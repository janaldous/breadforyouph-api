package com.janaldous.breadforyouph.testutil;

import java.util.Arrays;
import java.util.List;

import com.janaldous.breadforyouph.webfacade.dto.ProductDto;

public class ProductDtoMockFactory {

	public static List<ProductDto> getMockProducts() { 
		ProductDto productDto = new ProductDto();
		productDto.setId(991l);
		productDto.setQuantity(1);
		return Arrays.asList(productDto);
	}
	
}
