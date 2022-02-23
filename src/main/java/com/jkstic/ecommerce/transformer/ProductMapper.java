package com.jkstic.ecommerce.transformer;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.jkstic.ecommerce.dto.ProductDto;
import com.jkstic.ecommerce.models.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	
	ProductDto productToProductDto(Product entity);

	
	Product productDtotoProduct(ProductDto dto);
	
	List<ProductDto> listProductsToListProductsDto(List<Product> entity);
	
	List<Product> listProductsDtotoListProducts(List<ProductDto> dto);
}
