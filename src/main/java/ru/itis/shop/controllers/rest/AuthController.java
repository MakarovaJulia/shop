package ru.itis.shop.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.shop.dtos.SignInDto;
import ru.itis.shop.dtos.SignUpDto;
import ru.itis.shop.dtos.TokenDto;
import ru.itis.shop.dtos.UserDto;
import ru.itis.shop.services.UserService;

@RestController
@RequiredArgsConstructor
@Profile("rest")
public class AuthController {
    private final UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(userService.signIn(signInDto));
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signUp(signUpDto));
    }
}
