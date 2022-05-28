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
@RequestMapping("/orders")
@Profile("mvc")
public class OrdersController {

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping
    public String getAllUserOrders(Model model,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("orders", orderService.getAllUserOrders(userDetails.getUsername()));
        return "orders_page";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable("id") Long orderId,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails,
                                 Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "orders_page";
    }
}
