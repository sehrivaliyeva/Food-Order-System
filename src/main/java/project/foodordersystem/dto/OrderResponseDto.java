package project.foodordersystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import project.foodordersystem.enums.OrderStatus;
import project.foodordersystem.enums.Payment;

import java.util.List;

@Data
public class OrderResponseDto {
    private String address;
    private String restaurantName;
    private Payment payment;

    @JsonInclude(JsonInclude.Include.NON_NULL) // yalnız CARD olduqda göstər
    private String cardNumber;

    private OrderStatus status;

    private List<String> foodNames;
}
