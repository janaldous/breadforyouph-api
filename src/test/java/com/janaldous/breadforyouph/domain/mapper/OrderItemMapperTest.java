package com.janaldous.breadforyouph.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.OrderItem;
import com.janaldous.breadforyouph.data.Product;
import com.janaldous.breadforyouph.data.ProductRepository;

class OrderItemMapperTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private OrderItemMapper orderItemMapper;

	private Product mockProduct;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		mockProduct = new Product();
		mockProduct.setName("Original Banana Bread");
		mockProduct.setUnitPrice(BigDecimal.valueOf(165));
		mockProduct.setId(991l);
		Mockito.when(productRepository.findById(991l)).thenReturn(Optional.of(mockProduct));
	}

	@Test
	void testInvalidInputs() {
		assertThrows(IllegalArgumentException.class, () -> {
			orderItemMapper.toEntity(null, 0, null);
		});
	}
	
	@Test
	void testInvalidQuanitity() {
		Product product = new Product();
		assertThrows(IllegalArgumentException.class, () -> {
			orderItemMapper.toEntity(product, 0, null);
		});
	}

	@Test
	void testValidProductInput() {
		OrderDetail orderDetail = new OrderDetail();
		
		OrderItem result = orderItemMapper.toEntity(mockProduct, 1, orderDetail);

		assertEquals(1, result.getProductCount());
		assertNotNull(result.getProduct());
		assertEquals(mockProduct.getUnitPrice(), result.getTotal());
		assertEquals(mockProduct.getUnitPrice(), result.getBuyingPrice());
		assertEquals(orderDetail, result.getOrderDetail());
	}

	@Test
	void testValidMultipleProductInput() {
		OrderDetail orderDetail = new OrderDetail();

		OrderItem result = orderItemMapper.toEntity(mockProduct, 2, orderDetail);

		assertEquals(2, result.getProductCount());
		assertNotNull(result.getProduct());
		assertEquals(mockProduct.getUnitPrice().multiply(BigDecimal.valueOf(2)), result.getTotal());
		assertEquals(mockProduct.getUnitPrice(), result.getBuyingPrice());
		assertEquals(orderDetail, result.getOrderDetail());
	}

}
