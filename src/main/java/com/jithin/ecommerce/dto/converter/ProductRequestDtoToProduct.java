package com.jithin.ecommerce.dto.converter;

import com.jithin.ecommerce.dto.ProductRequestDto;
import com.jithin.ecommerce.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestDtoToProduct implements Converter<ProductRequestDto, Product> {
    @Override
    public Product convert(ProductRequestDto productRequestDto) {

        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setDescription(productRequestDto.getDescription());
        product.setQuantity(productRequestDto.getQuantity());
        product.setRating(productRequestDto.getRating());

        return product;
    }
}
