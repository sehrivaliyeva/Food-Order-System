package project.foodordersystem.service.security;


import project.foodordersystem.dto.security.AuthResponse;
import project.foodordersystem.dto.security.LoginDTO;
import project.foodordersystem.dto.security.RegisterDTO;

public interface AuthService {
    AuthResponse register(RegisterDTO request);
    AuthResponse login(LoginDTO request);

}
