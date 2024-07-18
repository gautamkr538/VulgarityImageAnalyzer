package com.springrest.VulgarityImageAnalyzer.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.springrest.VulgarityImageAnalyzer.exception.CustomApiException;
import com.springrest.VulgarityImageAnalyzer.exception.ImageProcessingException;
import com.springrest.VulgarityImageAnalyzer.service.VisionService;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

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
        }
    }
}
