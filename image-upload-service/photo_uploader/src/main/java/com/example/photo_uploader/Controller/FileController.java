package com.example.photo_uploader.Controller;

import com.example.photo_uploader.Service.MinioService;
import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return minioService.uploadFile(file);
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        InputStream fileStream = minioService.downloadFile(fileName);
        Resource resource = new InputStreamResource(fileStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping
    public ResponseEntity<List<String>> listFiles() {
        try {
            List<String> fileNames = new ArrayList<>();
            Iterable<Result<Item>> results = minioService.listFiles();

            for (Result<Item> result : results) {
                fileNames.add(result.get().objectName());
            }

            return ResponseEntity.ok(fileNames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}