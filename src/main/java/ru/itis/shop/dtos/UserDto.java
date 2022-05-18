package ru.itis.shop.dtos;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private Set<ProductDto> products;

    private Set<RoleDto> roles;
}
