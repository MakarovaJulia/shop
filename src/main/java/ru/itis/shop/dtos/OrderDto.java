package ru.itis.shop.dtos;

import lombok.*;
import ru.itis.shop.models.User;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;

    private User user;

    private Set<ProductDto> products;
}
