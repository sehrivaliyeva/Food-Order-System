package project.foodordersystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.foodordersystem.dto.MenuRequestDto;
import project.foodordersystem.dto.MenuResponseDto;
import project.foodordersystem.service.MenuService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/menu")
public class MenuController {
    private final MenuService service;

    @PostMapping
    public ResponseEntity<MenuResponseDto> create(@RequestBody MenuRequestDto menuRequestDto) {
        MenuResponseDto menuResponseDto = service.create(menuRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuResponseDto);
    }
}

