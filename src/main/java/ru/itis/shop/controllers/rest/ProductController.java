package ru.itis.shop.controllers.rest;

import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.dtos.ProductDto;
import ru.itis.shop.security.UserDetailsImpl;
import ru.itis.shop.services.ProductService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Profile("rest")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    public ResponseEntity<List<ProductDto>> getAllProducts(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ProductDto> getProductById(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto,
                                                    @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(productService.createProduct(productDto, userDetails.getUsername()));
    }

    @PutMapping
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
                                                    @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(productService.updateProduct(productDto, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") Long productId,
                                                    @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(productService.deleteProduct(productId, userDetails.getUsername()));
    }
}
