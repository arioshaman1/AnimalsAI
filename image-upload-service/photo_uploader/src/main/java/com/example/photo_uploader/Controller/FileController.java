package com.example.photo_uploader.Controller;

import com.example.photo_uploader.JWT.JwtUtil;
import com.example.photo_uploader.Service.MinioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final MinioService minioService;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Autowired
    public FileController(MinioService minioService, RestTemplate restTemplate, JwtUtil jwtUtil) {
        this.minioService = minioService;
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplate;
    }

    // Метод для получения токена из куки
    private String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Cookie name: " + cookie.getName() + ", value: " + cookie.getValue());
                if ("token".equals(cookie.getName())) {
                    System.out.println("Token found: " + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        System.out.println("Token not found in cookies");
        return null;
    }

    private String getUsernameFromToken(HttpServletRequest request) {
        String token = getTokenFromCookies(request);
        if (token != null) {
            System.out.println("Token extracted: " + token);
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                System.out.println("Username extracted: " + username);
                return username;
            } else {
                System.out.println("Token is invalid");
            }
        } else {
            System.out.println("Token is null");
        }
        return null;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            // Извлекаем имя пользователя из токена
            String username = getUsernameFromToken(request);

            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or username not found");
            }

            // Сохраняем файл в MinIO и получаем его URL
            String fileUrl = minioService.uploadFile(file, username);

            // Подготовка запроса к Python-сервису
            String pythonServiceUrl = "http://127.0.0.1:5000/predict";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Формируем тело запроса
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", file.getResource());
            body.add("username", username);  // Добавляем username в запрос

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Отправляем запрос в Python-микросервис
            ResponseEntity<String> response = restTemplate.postForEntity(pythonServiceUrl, requestEntity, String.class);

            // Возвращаем JSON-ответ от Flask обратно клиенту
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
