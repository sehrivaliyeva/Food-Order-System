package project.foodordersystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.foodordersystem.dto.FoodRequestDto;
import project.foodordersystem.dto.FoodResponseDto;
import project.foodordersystem.exception.NotFoundException;
import project.foodordersystem.model.Category;
import project.foodordersystem.model.Food;
import project.foodordersystem.repository.CategoryRepository;
import project.foodordersystem.repository.FoodRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public FoodResponseDto create(FoodRequestDto requestDto) {
        if (foodRepository.existsByFoodName(requestDto.getFoodName())) {
            throw new IllegalArgumentException("Food with name '" + requestDto.getFoodName() + "' already exists");
        }
        Category category = categoryRepository.findByCategoryName(requestDto.getCategoryName())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        Food food = modelMapper.map(requestDto, Food.class);
        food.setCategory(category);
        Food savedFood = foodRepository.save(food);
        return modelMapper.map(savedFood, FoodResponseDto.class);
    }

    public Food getByName(String foodName) {
        return foodRepository.findByFoodName(foodName)
                .orElseThrow(() -> new NotFoundException("Food not found: " + foodName));
    }

    public List<FoodResponseDto> getAll() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream()
                .map(category -> modelMapper.map(foods, FoodResponseDto.class))
                .collect(Collectors.toList());
    }

    public FoodResponseDto update(String foodName, FoodRequestDto requestDto) {
        Food existingFood = foodRepository.findByFoodName(foodName)
                .orElseThrow(() -> new NotFoundException("Food not found with name: " + foodName));

        if (foodRepository.existsByFoodName(requestDto.getFoodName()) &&
                !existingFood.getFoodName().equals(requestDto.getFoodName())) {
            throw new IllegalArgumentException("Another food with this name already exists: " + requestDto.getFoodName());
        }
        modelMapper.map(requestDto, existingFood);
        Food updatedFood = foodRepository.save(existingFood);
        return modelMapper.map(updatedFood, FoodResponseDto.class);
    }


    public String delete(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new NotFoundException("Food not found with id: " + id);
        }
        foodRepository.deleteById(id);
        return "Food deleted successfully";
    }


}
