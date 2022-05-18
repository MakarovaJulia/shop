package ru.itis.shop.services;

import ru.itis.shop.dtos.*;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    TokenDto signIn(SignInDto signInDto);

    UserDto signUp(SignUpDto signUpDto);

    UserDto update(UserDto userDto, String username);

    UserDto deleteUserById(Long userId, String username);

//    void transfer(String username, TransferDto transferDto);
//
//    Long getProductsCount(String username);
//
//    Double getProductsAmount(String username);

    void confirmUserAccount(String confirmationCode);

}
