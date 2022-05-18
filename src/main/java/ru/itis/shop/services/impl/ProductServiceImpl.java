package ru.itis.shop.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.shop.dtos.ProductDto;
import ru.itis.shop.dtos.mappers.ProductMapper;
import ru.itis.shop.models.Product;
import ru.itis.shop.models.Type;
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.ProductRepository;
import ru.itis.shop.repositories.UserRepository;
import ru.itis.shop.services.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    //TODO исправить метод
    @Override
    public List<ProductDto> getAllProducts() {
            return productMapper.map(productRepository.findAll());
    }

    @Override
    public ProductDto getProductById(Long productId) {
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                return productMapper.map(productOptional.get());
            }
            throw new IllegalArgumentException("no product with id = " + productId);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            Product product = productMapper.map(productDto);
            product.setType(new Type(1L, "DEBET", "DEBET"));
            product.setName(productDto.getName());
            product.setUser(userOptional.get());
            productRepository.save(product);
            return productMapper.map(product);
        }
        throw new IllegalArgumentException("no user with username = " + username);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            Optional<Product> productOptional = productRepository.findById(productDto.getId());
            if (productOptional.isPresent() &&
                    productOptional.get().getUser().getUsername().equals(username)) {
                productMapper.updateProductFromProductDto(productDto, productOptional.get());
                productRepository.save(productOptional.get());
                return productMapper.map(productOptional.get());
            }
            throw new IllegalArgumentException("No product with id = " + productDto.getId());
        }
        throw new IllegalArgumentException("No user with username = " + username);
    }

    @Override
    public ProductDto deleteProduct(Long productId, String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent() &&
                    productOptional.get().getUser().getUsername().equals(username)) {

                productRepository.deleteById(productOptional.get().getId());
                return productMapper.map(productOptional.get());
            }
        }
        throw new IllegalArgumentException("No user with username = " + username);
    }
}
