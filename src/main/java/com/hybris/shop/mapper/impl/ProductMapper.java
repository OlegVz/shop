package com.hybris.shop.mapper.impl;

import com.hybris.shop.dto.NewProductDto;
import com.hybris.shop.dto.ProductDto;
import com.hybris.shop.mapper.ProductMapperInterface;
import com.hybris.shop.model.Product;
import org.modelmapper.ModelMapper;

import java.util.Objects;

//@Component
public class ProductMapper implements ProductMapperInterface {

    private final ModelMapper modelMapper;

//    @Autowired
    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Product fromNewProductDtoToEntity(NewProductDto newProductDto) {
        return Objects.isNull(newProductDto) ? null : modelMapper.map(newProductDto, Product.class);
    }

    @Override
    public ProductDto fromEntityToProductDto(Product product) {
        return Objects.isNull(product) ? null : modelMapper.map(product, ProductDto.class);
    }
}