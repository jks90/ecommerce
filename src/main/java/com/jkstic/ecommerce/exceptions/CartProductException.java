package com.jkstic.ecommerce.exceptions;

public class CartProductException extends Exception  {

	private static final long serialVersionUID = 1L;

	public CartProductException(String errorMessage) {
        super(errorMessage);
    }
	
}
