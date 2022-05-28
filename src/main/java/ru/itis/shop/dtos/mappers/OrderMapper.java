package ru.itis.shop.dtos.mappers;

import org.mapstruct.*;
import ru.itis.shop.dtos.OrderDto;
import ru.itis.shop.models.Order;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    //    @Mapping(target = "user", ignore = true)
    Order map(OrderDto orderDto);

    //    @Mapping(target = "user", ignore = true)
    OrderDto map(Order order);

    List<OrderDto> map(List<Order> orders);

    Set<OrderDto> map(Set<Order> orders);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateOrderFromOrderDto(OrderDto orderDto, @MappingTarget Order order);
}