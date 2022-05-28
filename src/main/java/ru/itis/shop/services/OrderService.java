package ru.itis.shop.services;


import ru.itis.shop.dtos.OrderDto;
import ru.itis.shop.dtos.ProductDto;

import java.util.List;
import java.util.Set;

public interface OrderService {

    List<OrderDto> getAllUserOrders(String username);

    void addProductToOrder(ProductDto productDto);

    void removeProductFromOrder(ProductDto productDto);

    OrderDto getOrderById(Long productId);

    OrderDto createOrder(OrderDto orderDto, Long id, String username);

    OrderDto deleteOrder(Long orderId, String username);
}
