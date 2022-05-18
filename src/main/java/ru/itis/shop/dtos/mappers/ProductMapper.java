package ru.itis.shop.dtos.mappers;

import org.mapstruct.*;
import ru.itis.shop.dtos.ProductDto;
import ru.itis.shop.models.Product;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "user", ignore = true)
    Product map(ProductDto productDto);

    @Mapping(target = "user", ignore = true)
    ProductDto map(Product product);

    List<ProductDto> map(List<Product> products);

    Set<ProductDto> map(Set<Product> products);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateProductFromProductDto(ProductDto productDto, @MappingTarget Product product);
}