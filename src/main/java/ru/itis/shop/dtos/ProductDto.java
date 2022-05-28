package ru.itis.shop.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;

    private String name;

    private Long price;

    private UserDto user;

    private TypeDto type;
}
