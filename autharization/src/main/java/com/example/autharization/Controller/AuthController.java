package com.example.autharization.Controller;

import com.example.autharization.DTO.AuthEntityDTO;
import com.example.autharization.Service.AuthService;
import com.example.autharization.entities.AuthEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Регистрация и авторизация")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя", description = "Для регистрации нового пользователя нужно ввести username, password, email")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован")
    public ResponseEntity<AuthEntity> register(@RequestBody AuthEntityDTO authDTO) {
        System.out.println("Register endpoint called with username: " + authDTO.getUsername());
        AuthEntity authEntity = authService.registerUser(authDTO);
        return ResponseEntity.ok(authEntity);
    }

    @PostMapping("/login")
    @Operation(summary = "Логин пользователя", description = "Для логина нужно ввести username и password")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно залогинен, токен добавлен в куки")
    public ResponseEntity<String> login(@RequestBody AuthEntityDTO authEntityDTO, HttpServletResponse response) {
        try {
            String token = authService.authenticateUser(authEntityDTO.getUsername(), authEntityDTO.getPassword());

            // Создаем cookie с токеном
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true); // Защита от XSS
            cookie.setPath("/"); // Доступно для всех путей
            cookie.setMaxAge(86400); // Время жизни cookie (в секундах)

            // Добавляем cookie в ответ
            response.addCookie(cookie);

            return ResponseEntity.ok("Login successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid credentials: " + e.getMessage());
        }
    }
}