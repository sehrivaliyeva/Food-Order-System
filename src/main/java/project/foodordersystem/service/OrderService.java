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
            if (orderRequest.getCardNumber() == null || orderRequest.getCardNumber().length() != 16) {
                throw new IllegalArgumentException("Card Number must consist of exactly 16 characters");
            }
        } else {
            orderRequest.setCardNumber(null);
        }

        // Yemək adlarını yoxla
        if (orderRequest.getFoodNames() == null || orderRequest.getFoodNames().isEmpty()) {
            throw new IllegalArgumentException("Food list cannot be empty");
        }

        // Yeməkləri DB-dən götür və `Food` obyektlərinə çevir
        List<Food> foods = orderRequest.getFoodNames().stream()
                .map(name -> foodRepository.findByFoodName(name)
                        .orElseThrow(() -> new NotFoundException("Food not found: " + name)))
                .collect(Collectors.toList());

        // DTO-dan Entity yarat
        Order order = new Order();
        order.setAddress(orderRequest.getAddress());
        order.setRestaurantName(orderRequest.getRestaurantName());
        order.setPayment(orderRequest.getPayment());
        order.setCardNumber(orderRequest.getCardNumber());
        order.setFoods(foods);
        order.setStatus(OrderStatus.PENDING);

        // Məlumat bazasına əlavə et
        Order savedOrder = orderRepository.save(order);

        // **Cavab DTO yarat və məlumatları set et**
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setAddress(savedOrder.getAddress());
        responseDto.setRestaurantName(savedOrder.getRestaurantName());
        responseDto.setPayment(savedOrder.getPayment());
        responseDto.setCardNumber(savedOrder.getCardNumber());
        responseDto.setStatus(savedOrder.getStatus());

        // **`foodNames` siyahısını əl ilə set et**
        List<String> foodNames = savedOrder.getFoods().stream()
                .map(Food::getFoodName)
                .collect(Collectors.toList());
        responseDto.setFoodNames(foodNames);

        return responseDto;
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
