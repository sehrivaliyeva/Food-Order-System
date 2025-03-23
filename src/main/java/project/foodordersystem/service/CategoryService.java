package project.foodordersystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.foodordersystem.dto.CategoryRequestDto;
import project.foodordersystem.dto.CategoryResponseDto;
import project.foodordersystem.exception.NotFoundException;
import project.foodordersystem.model.Category;
import project.foodordersystem.model.Menu;
import project.foodordersystem.repository.CategoryRepository;
import project.foodordersystem.repository.MenuRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final MenuRepository menuRepository;

    public CategoryResponseDto create(CategoryRequestDto requestDto) {
        if (categoryRepository.existsByCategoryName(requestDto.getCategoryName())) {
            throw new IllegalArgumentException("Category with name '" + requestDto.getCategoryName() + "' already exists");
        }
        Menu menu = menuRepository.findByMenuName(requestDto.getMenuName())
                .orElseThrow(() -> new NotFoundException("Menu not found"));
        Category category = modelMapper.map(requestDto, Category.class);
        category.setMenu(menu);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryResponseDto.class);
    }


    public Category getByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new NotFoundException("Category not found: " + categoryName));
    }


    public List<CategoryResponseDto> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .collect(Collectors.toList());
    }

    public CategoryResponseDto update(String categoryName, CategoryRequestDto requestDto) {

        Category existingCategory = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new NotFoundException("Category not found with name: " + categoryName));

        if (categoryRepository.existsByCategoryName(requestDto.getCategoryName()) &&
                !existingCategory.getCategoryName().equals(requestDto.getCategoryName())) {
            throw new IllegalArgumentException("Another category with this name already exists: " + requestDto.getCategoryName());
        }
        modelMapper.map(requestDto, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryResponseDto.class);
    }



    public String delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
        return "Category deleted successfully";
    }


}
