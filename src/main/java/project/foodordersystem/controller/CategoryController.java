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
import project.foodordersystem.dto.CategoryRequestDto;
import project.foodordersystem.dto.CategoryResponseDto;
import project.foodordersystem.model.Category;
import project.foodordersystem.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@RequestBody @Valid CategoryRequestDto requestDto) {
        CategoryResponseDto responseDto = categoryService.create(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search/{categoryName}")
    public ResponseEntity<Category> getByName(@PathVariable("categoryName") String categoryName) {
        Category category = categoryService.getByName(categoryName);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/all/categories")
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        List<CategoryResponseDto> categoryResponseDtos = categoryService.getAll();
        return ResponseEntity.ok(categoryResponseDtos);

    }

    @PutMapping("/{categoryName}")
    public ResponseEntity<CategoryResponseDto> update(
            @PathVariable String categoryName,
            @RequestBody @Valid CategoryRequestDto requestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.update(categoryName, requestDto);
        return ResponseEntity.ok(categoryResponseDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        String message = categoryService.delete(id);
        return ResponseEntity.ok(message);
    }


}
