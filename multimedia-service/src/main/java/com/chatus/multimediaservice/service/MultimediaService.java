package com.chatus.multimediaservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class MultimediaService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public void uploadImage(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        Files.createDirectories(Paths.get(uploadDirectory));
        String uploadPath = Paths.get(uploadDirectory, originalFileName).toString();
        byte[] bytes = file.getBytes();
        Path filePath = Paths.get(uploadPath);
        Files.write(filePath, bytes);
    }

    public Resource downloadFile(String filename) throws IOException {
        String filePath = Paths.get(uploadDirectory, filename).toString();
        Resource resource = new UrlResource(Paths.get(filePath).toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found: " + filename);
        }
    }
}
