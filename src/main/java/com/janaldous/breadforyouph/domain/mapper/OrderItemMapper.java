package com.janaldous.breadforyouph.domain.mapper;

import java.math.BigDecimal;

import com.janaldous.breadforyouph.data.OrderItem;
import com.janaldous.breadforyouph.data.Product;

public class OrderItemMapper {

	public static OrderItem toEntity(Long quantity, Product product) {
		if (quantity == null || product == null)
			throw new IllegalArgumentException("quantity and product must not be null");

		OrderItem item = new OrderItem();
		item.setProductCount(quantity.intValue());
		item.setProduct(product);
		item.setBuyingPrice(product.getUnitPrice());
		item.setTotal(product.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));

		return item;
	}

}
