package project.foodordersystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.foodordersystem.dto.MenuRequestDto;
import project.foodordersystem.dto.MenuResponseDto;
import project.foodordersystem.model.Menu;
import project.foodordersystem.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public MenuResponseDto create(MenuRequestDto menuRequestDto) {
        Menu menu = modelMapper.map(menuRequestDto, Menu.class);
        return modelMapper.map(menuRepository.save(menu), MenuResponseDto.class);
    }
}
