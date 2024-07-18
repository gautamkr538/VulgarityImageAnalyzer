package com.springrest.VulgarityImageAnalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.springrest.VulgarityImageAnalyzer.service.ImageScanService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageScanService imageScanService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Save the uploaded file to a temporary location
            Path tempDir = Files.createTempDirectory("");
            Path tempFile = tempDir.resolve(file.getOriginalFilename());
            file.transferTo(tempFile.toFile());

            // Scan the image for vulgarity
            boolean isSafe = imageScanService.isImageSafe(tempFile.toString());

            // Delete the temporary file
            Files.delete(tempFile);
            Files.delete(tempDir);

            if (isSafe) {
                return ResponseEntity.ok("Image is safe and uploaded successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image contains inappropriate content.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the image.");
        }
    }
}
