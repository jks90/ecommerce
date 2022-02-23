package com.jkstic.ecommerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.jkstic.ecommerce.dto.CartDto;
import com.jkstic.ecommerce.dto.ProductDto;
import com.jkstic.ecommerce.exceptions.CartProductException;
import com.jkstic.ecommerce.models.Cart;
import com.jkstic.ecommerce.models.Product;
import com.jkstic.ecommerce.repositories.CartRepository;
import com.jkstic.ecommerce.repositories.ProductRepository;
import com.jkstic.ecommerce.services.impl.CartServiceImpl;

@SpringBootTest
public class CartServiceTest {

	@TestConfiguration
	static class CartServiceTestContextConfiguration {

		@Bean
		public CartService cartService() {
			return new CartServiceImpl();
		}

	}

	@Autowired
	private CartService cartService;

	@MockBean
	private CartRepository cartRepository;

	@MockBean
	private ProductRepository productRepository;

	@Test
	public void when_createCartNotProduct_then_ok() throws Exception {

		// Prepare
		List<ProductDto> listProducts = new ArrayList<ProductDto>();
		listProducts.add(ProductDto.builder().amount(0.00).description("test").build());
		CartDto cartDto = new CartDto();
		cartDto.setListadoProducts(listProducts);

		Cart cart = new Cart();
		cart.setId(1L);

		Mockito.when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(cart);

		// Use
		CartDto cartDtoResult = cartService.createCart(cartDto);

		// Assert
		assertEquals(1L, cartDtoResult.getId());

	}

	@Test
	public void when_createCartAndProduct_then_ok() throws Exception {

		// Prepare
		List<ProductDto> listProducts = new ArrayList<ProductDto>();
		listProducts.add(ProductDto.builder().amount(0.00).description("test").build());
		CartDto cartDto = new CartDto();
		cartDto.setListadoProducts(listProducts);

		Cart cart = new Cart();
		Product product = new Product();
		cart.setId(1L);

		Mockito.when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(cart);
		Mockito.when(productRepository.findByAmountAndDescription(Mockito.anyDouble(), Mockito.anyString()))
				.thenReturn(Optional.of(product));

		// Use
		CartDto cartDtoResult = cartService.createCart(cartDto);

		// Assert
		assertEquals(1L, cartDtoResult.getId());

	}

	@Test
	public void when_createCart_then_Exception() {

		// Prepare
		CartDto cartDto = new CartDto();

		// Use
		Exception exception = assertThrows(Exception.class, () -> cartService.createCart(cartDto));

		// Assert
		assertEquals(null, exception.getMessage());

	}

	@Test
	public void when_readCartById_then_ok() throws CartProductException {

		// Prepare
		Cart cart = new Cart();
		cart.setId(1L);

		Mockito.when(cartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cart));

		// Use
		CartDto cartDtoResult = cartService.readCartById(1L);

		// Assert
		assertEquals(1L, cartDtoResult.getId());
	}

	@Test
	public void when_readCartById_then_CartProductException() {

		// Prepare
		Cart cart = new Cart();
		cart.setId(1L);

		Mockito.when(cartRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		// Use
		CartProductException exception = assertThrows(CartProductException.class, () -> cartService.readCartById(1L));

		// Assert
		assertEquals("Cart not found.", exception.getMessage());
	}

	@Test
	public void when_updateProdutsCart_then_ok() throws CartProductException {

		// Prepare
		List<ProductDto> listProductsDto = new ArrayList<ProductDto>();
		listProductsDto.add(ProductDto.builder().amount(0.00).description("test").build());

		List<Product> listProducts = new ArrayList<Product>();
		listProducts.add(Product.builder().amount(0.00).description("test").build());

		Cart cart = new Cart();
		cart.setId(1L);
		cart.setListadoProducts(listProducts);

		Mockito.when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(cart);
		Mockito.when(cartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cart));

		// Use
		CartDto cartDtoResult = cartService.updateProdutsCart(1L, listProductsDto);

		// Assert
		assertEquals(1L, cartDtoResult.getId());
	}

	@Test
	public void when_updateProdutsCart_then_CartProductException() {

		// Use
		CartProductException exception = assertThrows(CartProductException.class,
				() -> cartService.updateProdutsCart(1L, null));

		// Assert
		assertEquals("Cart not found.", exception.getMessage());
	}
	
	@Test
	public void when_deleteCart_then_ok() throws CartProductException {
		
		// Prepare
		Cart cart = new Cart();
		Mockito.when(cartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cart));
		
		// use
		cartService.deleteCart(1L);
		
		assertTrue(true);
	}
	
	@Test
	public void when_deleteCart_then_CartProductException() {

		// Use
		CartProductException exception = assertThrows(CartProductException.class,
				() -> cartService.deleteCart(1L));

		// Assert
		assertEquals("Cart not found.", exception.getMessage());
	}
}
