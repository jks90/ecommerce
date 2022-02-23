package com.jkstic.ecommerce.config;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.jkstic.ecommerce.constants.Constants;
import com.jkstic.ecommerce.models.Product;
import com.jkstic.ecommerce.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@Configuration
public class SchedulerConfig {
	
	
	@Autowired
	private ProductRepository productRepository;

	@Scheduled(fixedRate = Constants.MINUT)
    public void ttldelete() {
		
		List<Product> listadoProducts = productRepository.findAll();
		
		listadoProducts.forEach( product -> {
			
			Date now = new Date();
			
			Long time = now.getTime() - product.getDataCreate().getTime();
			
			if(time > Constants.TTL) {
				log.info("DELETE: " + product.toString());
				productRepository.delete(product);
			}
			
		});
	}
}
