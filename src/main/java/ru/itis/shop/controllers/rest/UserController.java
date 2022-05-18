package ru.itis.shop.controllers.rest;

import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.dtos.UserDto;
import ru.itis.shop.security.UserDetailsImpl;
import ru.itis.shop.services.UserService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Profile("rest")
public class UserController {
    private final UserService userService;


    @GetMapping
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    public ResponseEntity<List<UserDto>> getUsersList() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                              @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.update(userDto, userDetails.getUsername()));
    }


    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId,
                                        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.deleteUserById(userId, userDetails.getUsername()));
    }
}
