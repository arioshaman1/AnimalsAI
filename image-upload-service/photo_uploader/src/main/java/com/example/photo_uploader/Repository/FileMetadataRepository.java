package com.example.photo_uploader.Repository;

import com.example.photo_uploader.entities.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

}
