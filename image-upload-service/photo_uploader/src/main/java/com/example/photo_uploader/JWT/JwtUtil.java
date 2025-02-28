package com.example.photo_uploader.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Фиксированный секретный ключ (в реальном приложении храните его в защищенном месте)
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("your-512-bit-secret-key-must-be-at-least-512-bits-long".getBytes());

    private static final long EXPIRATION_TIME = 864_000_000L; // 10 дней в миллисекундах

    // Генерация токена
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Устанавливаем username в качестве subject
                .setIssuedAt(new Date()) // Устанавливаем время создания токена
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Устанавливаем время истечения токена
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512) // Подписываем токен секретным ключом
                .compact();
    }

    // Проверка валидности токена
    public boolean validateToken(String token) {
        try {
            // Парсим токен и проверяем его подпись с использованием секретного ключа
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true; // Если исключения не было, то токен валидный
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage()); // Логирование ошибки
            return false; // Токен невалидный
        }
    }

    // Извлечение username из токена
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody(); // Извлекаем тело токена (Claims)
        return claims.getSubject(); // Извлекаем username из токена
    }
}