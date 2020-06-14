package com.janaldous.breadforyouph.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.janaldous.breadforyouph.data.OrderItem;
import com.janaldous.breadforyouph.data.Product;

class OrderItemMapperTest {

	@Test
	void testInvalidProductInput() {
		assertThrows(IllegalArgumentException.class, () -> {
			OrderItemMapper.toEntity(1l, null);
		});
	}

	@Test
	void testValidProductInput() {
		Long quantity = 1l;
		Product product = new Product();
		product.setName("Original Banana Bread");
		BigDecimal productPrice = BigDecimal.valueOf(165);
		product.setUnitPrice(productPrice);

		OrderItem result = OrderItemMapper.toEntity(quantity, product);

		assertEquals(quantity.intValue(), result.getProductCount());
		assertNotNull(result.getProduct());
		assertEquals(productPrice, result.getTotal());
		assertEquals(productPrice, result.getBuyingPrice());
	}

	@Test
	void testValidMultipleProductInput() {
		Long quantity = 2l;
		Product product = new Product();
		product.setName("Original Banana Bread");
		BigDecimal productPrice = BigDecimal.valueOf(165);
		product.setUnitPrice(productPrice);

		OrderItem result = OrderItemMapper.toEntity(quantity, product);

		assertEquals(quantity.intValue(), result.getProductCount());
		assertNotNull(result.getProduct());
		assertEquals(BigDecimal.valueOf(330), result.getTotal());
		assertEquals(productPrice, result.getBuyingPrice());
	}

	@Test
	void testInvalidProductAndQuantityInput() {
		assertThrows(IllegalArgumentException.class, () -> {
			OrderItemMapper.toEntity(null, null);
		});
	}

}
