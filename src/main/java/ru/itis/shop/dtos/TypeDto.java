package ru.itis.shop.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeDto {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
