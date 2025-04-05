package project.foodordersystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import project.foodordersystem.enums.Payment;

import java.util.List;

@Data
public class OrderRequestDto {

    @NotBlank(message = "Address not be empty or null")
    private String address;

    @NotBlank(message = "Restaurant not be empty or null")
    private String restaurantName;


    @NotNull(message = "Payment type cannot be null")
    private Payment payment;

    private String cardNumber;

    @Size(min = 1)
    private List<String> foodNames;

}
