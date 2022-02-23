package com.jkstic.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jkstic.ecommerce.constants.Constants;
import com.jkstic.ecommerce.dto.CartDto;
import com.jkstic.ecommerce.dto.ProductDto;
import com.jkstic.ecommerce.exceptions.CartProductException;
import com.jkstic.ecommerce.services.CartService;


@RestController
@RequestMapping(value = "/api")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	
	@PostMapping(value = "/cart")
	public ResponseEntity<?> createCart(@RequestBody CartDto cart){
		try {
			CartDto result = cartService.createCart(cart);
			return new ResponseEntity<CartDto>(result, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/cart/idCart/{idCart}")
	public ResponseEntity<?> viewCartById(@PathVariable Long idCart){
		try {
			CartDto result = cartService.readCartById(idCart);
			return new ResponseEntity<CartDto>(result, HttpStatus.OK);
		}catch (CartProductException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/cart/idCart/{idCart}")
	public ResponseEntity<?> updateCart(@PathVariable Long idCart, @RequestBody List<ProductDto> listProducts){
		try {
			CartDto result = cartService.updateProdutsCart(idCart, listProducts);
			return new ResponseEntity<CartDto>(result, HttpStatus.OK);
		}catch (CartProductException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/cart/idCart/{idCart}")
	public ResponseEntity<?> deleteCart(@PathVariable Long idCart){
		try {
			cartService.deleteCart(idCart);
			return new ResponseEntity<String>(Constants.DELETE_OK, HttpStatus.OK);
		}catch (CartProductException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
