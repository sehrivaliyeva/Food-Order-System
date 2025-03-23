package project.foodordersystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.foodordersystem.dto.OrderRequestDto;
import project.foodordersystem.dto.OrderResponseDto;
import project.foodordersystem.enums.OrderStatus;
import project.foodordersystem.enums.Payment;
import project.foodordersystem.exception.NotFoundException;
import project.foodordersystem.model.Food;
import project.foodordersystem.model.Order;
import project.foodordersystem.repository.FoodRepository;
import project.foodordersystem.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final FoodRepository foodRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {

        if (orderRequest.getPayment() == Payment.CARD) {
            if (orderRequest.getCardNumber() == null || orderRequest.getCardNumber().length() < 16) {
                throw new IllegalArgumentException("Card Number consist of 16 characters");
            }
        }

        List<Food> foods = orderRequest.getFoodNames().stream()
                .map(name -> foodRepository.findByFoodName(name)
                        .orElseThrow(() -> new NotFoundException("Food not found: " + name)))
                .collect(Collectors.toList());
        Order order = modelMapper.map(orderRequest, Order.class);
        order.setFoods(foods);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderResponseDto.class);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
