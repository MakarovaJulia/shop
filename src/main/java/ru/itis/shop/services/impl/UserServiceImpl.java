package ru.itis.shop.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.shop.dtos.*;
import ru.itis.shop.dtos.mappers.UserMapper;
import ru.itis.shop.models.Role;
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.ProductRepository;
import ru.itis.shop.repositories.UserRepository;
import ru.itis.shop.services.UserService;
import ru.itis.shop.util.EmailUtil;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${cards.credit.min-amount}")
    private Double minAmountOfCreditCard;
    private final EmailUtil emailUtil;

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.map(userRepository.findAll());
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent())
            return userMapper.map(userOptional.get());
        throw new IllegalArgumentException("No user with id = " + userId);
    }

    @Override
    public TokenDto signIn(SignInDto signInDto) {
        Optional<User> userOptional = userRepository.findUserByUsername(signInDto.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getHashPassword())) {
                String tokenValue = Jwts.builder()
                        .setSubject(String.valueOf(user.getId()))
                        .claim("username", user.getUsername())
                        .claim("roles", user.getRoles())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
                return TokenDto.builder().token(tokenValue).build();
            }
        }
        throw new IllegalArgumentException("Bad username or password");
    }

    @Override
    public UserDto signUp(SignUpDto signUpDto) {
        User user = User.builder()
                .username(signUpDto.getUsername())
                .hashPassword(passwordEncoder.encode(signUpDto.getPassword()))
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .confirmCode(UUID.randomUUID().toString())
                .state(User.State.NOT_CONFIRMED)
                .createTime(Instant.now())
                .updateTime(Instant.now())
                .roles(Collections.singleton(new Role(1l, "USER")))
                .build();

        userRepository.save(user);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("first_name", user.getFirstName());
        model.put("last_name", user.getLastName());
        model.put("confirm_code", user.getConfirmCode());


        emailUtil.sendMail(user.getEmail(), "confirm", model);

        return userMapper.map(user);
    }

    @Override
    public UserDto update(UserDto userDto, String username) {
        Optional<User> userOptional = userRepository.findById(userDto.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getUsername().equals(username)) {
                userMapper.updateUserFromUserDto(userDto, user);
                user.setUpdateTime(Instant.now());
                userRepository.save(user);
                return userMapper.map(user);
            }
            throw new IllegalArgumentException("cant delete user");
        }
        throw new IllegalArgumentException("bad user");
    }

    @Override
    public UserDto deleteUserById(Long userId, String username) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getUsername().equals(username)) {
                userRepository.deleteById(userId);
                return userMapper.map(user);
            }
            throw new IllegalArgumentException("cant delete account");
        }
        throw new IllegalArgumentException("bad user id = " + userId);
    }

//    @Override
//    public Long getProductsCount(String username) {
//        return userRepository.getProductsCount(username);
//    }
//
//    @Override
//    public Double getProductsAmount(String username) {
//        return userRepository.getProductsAmount(username);
//    }

    @Override
    public void confirmUserAccount(String confirmationCode) {
        Optional<User> account = userRepository.findUserByConfirmCode(confirmationCode);
        System.out.println(account.get().getConfirmCode());
        System.out.println(account.get().getId());
        System.out.println(account.get().getFirstName());
        if(account.isPresent()){
            userRepository.updateUserState(User.State.CONFIRMED);
        }
        System.out.println(account.get().getState());
    }
}
