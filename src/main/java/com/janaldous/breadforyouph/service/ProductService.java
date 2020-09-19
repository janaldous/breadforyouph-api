package com.janaldous.breadforyouph.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.janaldous.breadforyouph.data.Product;
import com.janaldous.breadforyouph.data.ProductRepository;

@Component
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public Page<Product> getProducts(int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);
		return productRepository.findAll(pageRequest);
	}

}
