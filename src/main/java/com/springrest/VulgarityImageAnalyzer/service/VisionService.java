package com.springrest.VulgarityImageAnalyzer.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.springrest.VulgarityImageAnalyzer.exception.CustomApiException;
import com.springrest.VulgarityImageAnalyzer.exception.ImageProcessingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class VisionService {

    public boolean isSafeImage(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length == 0) {
            throw new ImageProcessingException("Image file is empty.");
        }

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            Image img = Image.newBuilder().setContent(ByteString.copyFrom(imageBytes)).build();
            Feature safeSearchFeature = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();

            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(safeSearchFeature)
                    .setImage(img)
                    .build();

            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(List.of(request));
            AnnotateImageResponse annotateImageResponse = response.getResponsesList().get(0);

            if (annotateImageResponse.hasError()) {
                throw new CustomApiException("Google Vision API error: " + annotateImageResponse.getError().getMessage());
            }

            SafeSearchAnnotation annotation = annotateImageResponse.getSafeSearchAnnotation();
            return isSafe(annotation);

        } catch (IOException e) {
            throw new CustomApiException("Failed to connect to Google Vision API: " + e.getMessage());
        }
    }

    private boolean isSafe(SafeSearchAnnotation annotation) {
        // Check each category in the SafeSearchAnnotation
        return annotation.getAdult() != Likelihood.LIKELY &&
               annotation.getViolence() != Likelihood.LIKELY &&
               annotation.getRacy() != Likelihood.LIKELY;
    }
}
