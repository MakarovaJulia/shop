package ru.itis.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.dtos.OrderDto;
import ru.itis.shop.dtos.ProductDto;
import ru.itis.shop.security.UserDetailsImpl;
import ru.itis.shop.services.OrderService;
import ru.itis.shop.services.ProductService;
import ru.itis.shop.services.UserService;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
@Profile("mvc")
public class ProductController {
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products_page";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable("id") Long productId,
                                 Model model) {
        model.addAttribute("product", productService.getProductById(productId));
        return "product_page";
    }

    @GetMapping("/add")
    public String getProductAddPage(Model model) {
        model.addAttribute("product", new ProductDto());
        return "product_add_page";
    }

    @PostMapping("/add")
    public String createProduct(@ModelAttribute ProductDto productDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProductDto productDto1 = productService.createProduct(productDto, userDetails.getUsername());
        return "redirect:/products/" + productDto1.getId();
    }

    @PutMapping
    public void updateProduct(@ModelAttribute ProductDto productDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        productService.updateProduct(productDto, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long productId,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        productService.deleteProduct(productId, userDetails.getUsername());
        return "redirect:/";
    }

    @PostMapping("/{id}")
    public String createOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @ModelAttribute OrderDto orderDto, @PathVariable("id") Long productId) {
        orderService.createOrder(orderDto, productId, userDetails.getUsername());
        return "redirect:/";
    }
}
