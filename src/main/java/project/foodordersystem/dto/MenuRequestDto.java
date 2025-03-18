package project.foodordersystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDto {
    @NotEmpty(message = " this section must not be a null")
    @NotNull
    private String menuName;
    @NotEmpty(message = "this section must not be a null")
    private String menuDescription;
}
