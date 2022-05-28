package ru.itis.shop.dtos.mappers;

import org.mapstruct.*;
import ru.itis.shop.dtos.OrderDto;
import ru.itis.shop.models.Order;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order map(OrderDto orderDto);

    OrderDto map(Order order);

    List<OrderDto> map(List<Order> orders);

    Set<OrderDto> map(Set<Order> orders);
}