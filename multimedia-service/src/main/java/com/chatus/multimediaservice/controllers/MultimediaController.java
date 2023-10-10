package com.chatus.multimediaservice.controllers;

import com.chatus.multimediaservice.service.MultimediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/api/multimedia")
public class MultimediaController {

    @Autowired
    private MultimediaService multimediaService;

    @PostMapping("/v1/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload.");
            }
            multimediaService.uploadImage(file);
            return ResponseEntity.ok().body("Image uploaded successfully.");
        } catch (Exception e) {
            log.error("Error loading image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error loading image");
        }
    }

    @GetMapping("/v1/download-image/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("filename") String filename) {
        try {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .body(multimediaService.downloadFile(filename));
        } catch (FileNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            log.error("Error downloading image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

