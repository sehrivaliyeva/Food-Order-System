package project.foodordersystem.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.foodordersystem.dto.OrderRequestDto;
import project.foodordersystem.dto.OrderResponseDto;
import project.foodordersystem.enums.OrderStatus;
import project.foodordersystem.enums.Payment;
import project.foodordersystem.exception.NotFoundException;
import project.foodordersystem.model.Food;
import project.foodordersystem.model.Order;
import project.foodordersystem.repository.FoodRepository;
import project.foodordersystem.repository.OrderRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder_withCardPayment_success() {
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setAddress("Baku");
        requestDto.setRestaurantName("Pizza Hut");
        requestDto.setPayment(Payment.CARD);
        requestDto.setCardNumber("1234567890123456");
        requestDto.setFoodNames(List.of("Pizza", "Burger"));


        Food pizza = new Food();
        pizza.setId(1L);
        pizza.setFoodName("Pizza");
        pizza.setDescription("Cheesy and delicious");

        Food burger = new Food();
        burger.setId(2L);
        burger.setFoodName("Burger");
        burger.setDescription("Juicy beef burger");


        Order savedOrder = new Order();
        savedOrder.setAddress("Baku");
        savedOrder.setRestaurantName("Pizza Hut");
        savedOrder.setPayment(Payment.CARD);
        savedOrder.setCardNumber("1234567890123456");
        savedOrder.setFoods(List.of(pizza, burger));
        savedOrder.setStatus(OrderStatus.PENDING);


        when(foodRepository.findByFoodName("Pizza")).thenReturn(Optional.of(pizza));
        when(foodRepository.findByFoodName("Burger")).thenReturn(Optional.of(burger));
        verify(orderRepository).save(Mockito.<Order>any());



        OrderResponseDto response = orderService.createOrder(requestDto);

        assertEquals("Baku", response.getAddress());
        assertEquals("Pizza Hut", response.getRestaurantName());
        assertEquals(Payment.CARD, response.getPayment());
        assertEquals("1234567890123456", response.getCardNumber());
        assertEquals(OrderStatus.PENDING, response.getStatus());
        assertEquals(List.of("Pizza", "Burger"), response.getFoodNames());

        verify(foodRepository, times(1)).findByFoodName("Pizza");
        verify(foodRepository, times(1)).findByFoodName("Burger");
       // verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderRepository, times(1)).save(Mockito.<Order>any());


    }

    @Test
    void testCreateOrder_withInvalidCardNumber_shouldThrowException() {
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setPayment(Payment.CARD);
        requestDto.setCardNumber("12345");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(requestDto)
        );

        assertEquals("Card Number must consist of exactly 16 characters", exception.getMessage());
    }

    @Test
    void testCreateOrder_withEmptyFoodList_shouldThrowException() {
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setPayment(Payment.CASH);
        requestDto.setFoodNames(Collections.emptyList());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(requestDto)
        );

        assertEquals("Food list cannot be empty", exception.getMessage());
    }

    @Test
    void testCreateOrder_withUnknownFood_shouldThrowNotFoundException() {
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setPayment(Payment.CASH);
        requestDto.setFoodNames(List.of("UnknownFood"));

        when(foodRepository.findByFoodName("UnknownFood")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> orderService.createOrder(requestDto)
        );

        assertEquals("Food not found: UnknownFood", exception.getMessage());
    }
}