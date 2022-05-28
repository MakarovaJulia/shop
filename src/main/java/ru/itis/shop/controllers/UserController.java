package ru.itis.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.dtos.UserDto;
import ru.itis.shop.security.UserDetailsImpl;
import ru.itis.shop.services.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Profile("mvc")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") Long userId,
                              Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        return "user_page";
    }

    @PutMapping
    public void updateUser(@ModelAttribute UserDto userDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.update(userDto, userDetails.getUsername());
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long userId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUserById(userId, userDetails.getUsername());
        return "redirect:/";
    }

//    @GetMapping("/productsCount")
//    public @ResponseBody Long getProductsCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return userService.getProductsCount(userDetails.getUsername());
//    }
//
//    @GetMapping("/productsAmount")
//    public @ResponseBody Double getProductsAmount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return userService.getProductsAmount(userDetails.getUsername());
//    }

}
