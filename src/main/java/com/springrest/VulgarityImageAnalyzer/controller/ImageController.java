package com.springrest.VulgarityImageAnalyzer.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.springrest.VulgarityImageAnalyzer.exception.CustomApiException;
import com.springrest.VulgarityImageAnalyzer.exception.ImageProcessingException;
import com.springrest.VulgarityImageAnalyzer.service.VisionService;

import java.io.IOException;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.springrest.VulgarityImageAnalyzer.service.ImageScanService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
>>>>>>> 3ea20124ebf6dc2a3b63569bf9754ff5747e0939

@RestController
@RequestMapping("/api/images")
public class ImageController {

<<<<<<< HEAD
    private final VisionService visionService;

    public ImageController(VisionService visionService) {
        this.visionService = visionService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeImage(@RequestParam("file") MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            boolean isSafe = visionService.isSafeImage(imageBytes);
            if (!isSafe) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("The image contains inappropriate content.");
            }
            return ResponseEntity.ok("The image is safe.");
        } catch (ImageProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Image processing error: " + e.getMessage());
        } catch (CustomApiException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("API error: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
=======
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
>>>>>>> 3ea20124ebf6dc2a3b63569bf9754ff5747e0939
        }
    }
}
