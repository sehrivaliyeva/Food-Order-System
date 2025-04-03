package project.foodordersystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.foodordersystem.dto.FoodRequestDto;
import project.foodordersystem.dto.FoodResponseDto;
import project.foodordersystem.model.Food;
import project.foodordersystem.service.FoodService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/food")
public class FoodController {

    private final FoodService foodService;


    @PostMapping
    public ResponseEntity<FoodResponseDto> create(@RequestBody @Valid FoodRequestDto requestDto) {
        FoodResponseDto responseDto = foodService.create(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search/{foodName}")
    public ResponseEntity<Food> getByName(@PathVariable("foodName") String foodName) {
        Food food = foodService.getByName(foodName);
        return ResponseEntity.ok(food);
    }

    @GetMapping("/all/foods")
    public ResponseEntity<List<FoodResponseDto>> getAll() {
        List<FoodResponseDto> foodResponseDtos = foodService.getAll();
        return ResponseEntity.ok(foodResponseDtos);

    }

    @PutMapping("/{foodName}")
    public ResponseEntity<FoodResponseDto> update(
            @PathVariable String foodName,
            @RequestBody @Valid FoodRequestDto requestDto) {
        FoodResponseDto foodResponseDto = foodService.update(foodName, requestDto);
        return ResponseEntity.ok(foodResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        String message = foodService.delete(id);
        return ResponseEntity.ok(message);
    }


}
