package project.foodordersystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.foodordersystem.dto.MenuRequestDto;
import project.foodordersystem.dto.MenuResponseDto;
import project.foodordersystem.model.Menu;
import project.foodordersystem.service.MenuService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/search/{menuName}")
    public ResponseEntity<Optional<Menu>> getMenusByName(@PathVariable String menuName) {
        Optional<Menu> menu = menuService.getMenusByName(menuName);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuResponseDto>> getAllMenus() {
        List<MenuResponseDto> menus = menuService.getAllMenus();
        return ResponseEntity.ok(menus);
    }

    @PostMapping
    public ResponseEntity<MenuResponseDto> create(@Valid @RequestBody MenuRequestDto menuRequestDto) {
        MenuResponseDto menuResponseDto = menuService.create(menuRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @PathVariable Long id,
            @RequestBody MenuRequestDto menuRequestDto) {
        MenuResponseDto updatedMenu = menuService.updateMenu(id, menuRequestDto);
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }


}



