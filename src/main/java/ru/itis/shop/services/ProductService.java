package ru.itis.shop.services;

import ru.itis.shop.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long productId);

    ProductDto createProduct(ProductDto productDto, String username);

    ProductDto updateProduct(ProductDto productDto, String username);

    ProductDto deleteProduct(Long productId, String username);
}
