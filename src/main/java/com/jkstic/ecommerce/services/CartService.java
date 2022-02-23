package com.jkstic.ecommerce.services;

import java.util.List;

import com.jkstic.ecommerce.dto.CartDto;
import com.jkstic.ecommerce.dto.ProductDto;
import com.jkstic.ecommerce.exceptions.CartProductException;

public interface CartService {
	
	public CartDto createCart(CartDto cart) throws Exception;
	public CartDto readCartById(Long idCart) throws CartProductException;
	public CartDto updateProdutsCart(Long idCart,List<ProductDto> listProducts) throws CartProductException;
	public void deleteCart(Long idCart) throws CartProductException;
}
