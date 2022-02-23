package com.jkstic.ecommerce.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.jkstic.ecommerce.dto.CartDto;
import com.jkstic.ecommerce.models.Cart;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {
	
	CartMapper INSTANCE =  Mappers.getMapper(CartMapper.class);

	@Mapping(target = "listadoProducts", source = "listadoProducts")
	CartDto cartToCartDto(Cart entity);
	
	@Mapping(target = "listadoProducts", source = "listadoProducts")
	Cart cartDtotoCart(CartDto dto);
}
