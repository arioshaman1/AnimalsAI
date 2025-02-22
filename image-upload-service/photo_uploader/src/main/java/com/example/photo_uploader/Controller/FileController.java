package com.example.photo_uploader.Controller;

import com.example.photo_uploader.Service.MinioService;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final MinioService minioService;
    private final RestTemplate restTemplate;

    public FileController(MinioService minioService, RestTemplate restTemplate) {
        this.minioService = minioService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Сохраняем файл в MinIO и получаем его URL
            String fileUrl = minioService.uploadFile(file);

            // Подготовка запроса к Python-сервису
            String pythonServiceUrl = "http://127.0.0.1:5000/predict";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Формируем тело запроса
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", file.getResource());

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