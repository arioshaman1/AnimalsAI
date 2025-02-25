package com.example.autharization.Controller;

import com.example.autharization.Service.AuthService;
import com.example.autharization.entities.AuthEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthEntity> register(@RequestBody AuthEntity authEntity) {
        return ResponseEntity.ok(authService.registerUser(authEntity));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        try {
            String token = authService.authenticateUser(username, password);

            // Создаем cookie с токеном
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true); // Защита от XSS
            cookie.setSecure(true); // Только для HTTPS
            cookie.setPath("/"); // Доступно для всех путей
            cookie.setMaxAge(86400); // Время жизни cookie (в секундах)

            // Добавляем cookie в ответ
            response.addCookie(cookie);

            return ResponseEntity.ok("Login successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
