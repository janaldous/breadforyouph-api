package com.janaldous.breadforyouph.domain.mapper;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.OrderItem;
import com.janaldous.breadforyouph.data.Product;

@Component
public class OrderItemMapper {

	public OrderItem toEntity(Product product, int quantity, OrderDetail orderDetail) {
		if (product == null || quantity <= 0 || orderDetail == null)
			throw new IllegalArgumentException("quantity must be greater than zero and order must not be null");

		OrderItem item = new OrderItem(orderDetail);
		item.setProductCount(quantity);
		item.setProduct(product);
		item.setBuyingPrice(product.getUnitPrice());
		item.setTotal(product.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));

		return item;
	}

}
