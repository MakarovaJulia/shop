package ru.itis.shop.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.shop.dtos.OrderDto;
import ru.itis.shop.dtos.ProductDto;
import ru.itis.shop.dtos.mappers.OrderMapper;
import ru.itis.shop.models.Order;
import ru.itis.shop.models.Product;
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.OrderRepository;
import ru.itis.shop.repositories.ProductRepository;
import ru.itis.shop.repositories.UserRepository;
import ru.itis.shop.services.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    Set<Product> orderedProducts;

    @Override
    public List<OrderDto> getAllUserOrders(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            return orderMapper.map(orderRepository.findAllByUser(userOptional));
        }
        throw new IllegalArgumentException("no user");
    }

    @Override
    public void addProductToOrder(ProductDto productDto) {
        Optional<Product> product = productRepository.findById(productDto.getId());
        if(product.isPresent()) {
            orderedProducts.add(product.get());
        }
        System.out.println(orderedProducts);
    }

    @Override
    public void removeProductFromOrder(ProductDto productDto) {
        Optional<Product> product = productRepository.findById(productDto.getId());
        if(product.isPresent()) {
            orderedProducts.remove(product.get());
        }
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            return orderMapper.map(orderOptional.get());
        }
        throw new IllegalArgumentException("no order with id = " + orderId);
    }

    //TODO добавить список заказов
    @Override
    public OrderDto createOrder(OrderDto orderDto, Long id, String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        Optional<Product> product = productRepository.findById(id);
        orderedProducts.add(product.get());
        if (userOptional.isPresent()) {
            Order order = orderMapper.map(orderDto);
            order.setProducts(orderedProducts);
//            order.setState(Order.State.NOT_DELIVERED);
            order.setUser(userOptional.get());
            orderRepository.save(order);
            orderedProducts.clear();
            return orderMapper.map(order);
        }
        throw new IllegalArgumentException("no user with username = " + username);
    }

    @Override
    public OrderDto deleteOrder(Long orderId, String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isPresent() &&
                    orderOptional.get().getUser().getUsername().equals(username)) {

                orderRepository.deleteById(orderOptional.get().getId());
                return orderMapper.map(orderOptional.get());
            }
        }
        throw new IllegalArgumentException("No user with username = " + username);
    }
}
