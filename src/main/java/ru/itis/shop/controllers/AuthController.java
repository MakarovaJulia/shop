package ru.itis.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.shop.dtos.SignUpDto;
import ru.itis.shop.services.UserService;

@Controller
@RequiredArgsConstructor
@Profile("mvc")
public class AuthController {
    private final UserService userService;

    @GetMapping("/signUp")
    public String getSignUpPage(Model model) {
        model.addAttribute("signUpDto", new SignUpDto());
        return "sign_up";
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute SignUpDto signUpDto) {
        userService.signUp(signUpDto);
        return "redirect:/signIn";
    }

    @GetMapping("/signIn")
    public String getSignInPage() {
        return "sign_in";
    }
}
