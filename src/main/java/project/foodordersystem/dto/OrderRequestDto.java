package project.foodordersystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import project.foodordersystem.enums.OrderStatus;
import project.foodordersystem.enums.Payment;

import java.util.List;

@Data
public class OrderRequestDto {

    @NotBlank(message = "Address not be empty or null")
    private String address;

    @NotBlank(message = "Restaurant not be empty or null")
    private String restaurantName;

    @NotBlank(message = "Payment not be empty or null")
    private Payment payment;

    @Size(min = 16, message = "Card number consist  16 character")
    @NotBlank(message = "CardNumber not be empty or null")
    private String cardNumber;

    @NotBlank(message = "Foods not be empty or null")
    private List<String> foodNames;

}
