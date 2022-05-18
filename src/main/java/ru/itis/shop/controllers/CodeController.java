package ru.itis.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.shop.services.UserService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/confirm-account")
public class CodeController {

    private final UserService userService;

    @GetMapping
    @PostMapping
    public String confirmUserAccount(@RequestParam("code") String confirmationCode) {
        userService.confirmUserAccount(confirmationCode);
        return "redirect:/signUp";
    }
}
