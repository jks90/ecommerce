package com.jkstic.ecommerce.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jkstic.ecommerce.constants.Constants;
import com.jkstic.ecommerce.dto.CartDto;
import com.jkstic.ecommerce.dto.ProductDto;
import com.jkstic.ecommerce.exceptions.CartProductException;
import com.jkstic.ecommerce.models.Cart;
import com.jkstic.ecommerce.models.Product;
import com.jkstic.ecommerce.repositories.CartRepository;
import com.jkstic.ecommerce.repositories.ProductRepository;
import com.jkstic.ecommerce.services.CartService;
import com.jkstic.ecommerce.transformer.CartMapper;
import com.jkstic.ecommerce.transformer.ProductMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;

	@Override
	public CartDto createCart(CartDto cart) throws Exception {
		log.info("createCart");

		try {

			Cart cartTarget = new Cart();
			cartTarget.setListadoProducts(isProductInBD(cart.getListadoProducts()));

			CartDto cartDtoSaved = cartMapper.cartToCartDto(cartRepository.save(cartTarget));
			cartDtoSaved.setListadoProducts(cart.getListadoProducts());

			return cartDtoSaved;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public CartDto readCartById(Long idCart) throws CartProductException {
		log.info("readCartById");
		Optional<Cart> cartTarget = cartRepository.findById(idCart);
		if (cartTarget.isPresent()) {
			return cartMapper.cartToCartDto(cartTarget.get());
		} else {
			throw new CartProductException(Constants.CART_NOT_FOUND);
		}
	}

	@Override
	public CartDto updateProdutsCart(Long idCart, List<ProductDto> listProducts)
			throws CartProductException {
		log.info("updateProdutsCart");

		Optional<Cart> cartTarget = cartRepository.findById(idCart);
		if (cartTarget.isPresent()) {

			List<Product> oldListProductMoreNews = cartTarget.get().getListadoProducts();
			oldListProductMoreNews.addAll(isProductInBD(listProducts));
			cartTarget.get().setListadoProducts(oldListProductMoreNews);

			CartDto cartDtoSaved = cartMapper.cartToCartDto(cartRepository.save(cartTarget.get()));
			cartDtoSaved.setListadoProducts(productMapper.listProductsToListProductsDto(oldListProductMoreNews));

			return cartDtoSaved;
		} else {
			throw new CartProductException(Constants.CART_NOT_FOUND);
		}
	}

	@Override
	public void deleteCart(Long idCart) throws CartProductException {
		log.info("deleteCart");
		Optional<Cart> cartTarget = cartRepository.findById(idCart);
		if (cartTarget.isPresent()) {
			cartRepository.delete(cartTarget.get());
		} else {
			throw new CartProductException(Constants.CART_NOT_FOUND);
		}
	}

	private List<Product> isProductInBD(List<ProductDto> listProducts) {
		List<Product> listProductTarget = new ArrayList<Product>();
		for (ProductDto product : listProducts) {
			Optional<Product> productTarget = productRepository.findByAmountAndDescription(product.getAmount(),
					product.getDescription());
			if (productTarget.isPresent()) {
				listProductTarget.add(productTarget.get());
			} else {
				Product productToSave = productMapper.productDtotoProduct(product);
				productToSave.setDataCreate(new Date());
				listProductTarget.add(productRepository.save(productToSave));
			}
		}
		return listProductTarget;
	}

}
