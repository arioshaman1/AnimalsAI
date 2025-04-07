package com.example.autharization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // Указываем источник, с которого разрешаем запросы
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Разрешенные методы
                .allowedHeaders("Authorization", "Content-Type")  // Разрешенные заголовки
                .allowCredentials(true);  // Разрешаем отправку учетных данных
    }
}