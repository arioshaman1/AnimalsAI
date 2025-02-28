package com.example.photo_uploader.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String fileName;
    private String originalFileName;
    private long fileSize;
    private String fileType;
    private LocalDateTime uploadTime;

}