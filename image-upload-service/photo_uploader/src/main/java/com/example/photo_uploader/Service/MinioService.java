package com.example.photo_uploader.Service;
import com.example.photo_uploader.Repository.FileMetadataRepository;
import com.example.photo_uploader.entities.FileMetadata;
import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Value("${minio.bucket}")
    private String bucketName;



    public String uploadFile(MultipartFile file) {
        try {
            // Проверяем, существует ли бакет
            boolean isExist = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );

            if (!isExist) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
            }

            // Генерируем уникальное имя файла
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            // Загружаем файл в MinIO
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }
            FileMetadata metadata = new FileMetadata();
            metadata.setFileName(fileName);
            metadata.setOriginalFileName(file.getOriginalFilename());
            metadata.setFileSize(file.getSize());
            metadata.setFileType(file.getContentType());
            metadata.setUploadTime(LocalDateTime.now());
            fileMetadataRepository.save(metadata);

            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке файла в MinIO", e);
        }
    }
    public InputStream downloadFile(String fileName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при скачивании файла", e);
        }
    }
}