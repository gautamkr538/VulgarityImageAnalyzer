package com.springrest.VulgarityImageAnalyzer.service;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.SafeSearchAnnotation;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

@Service
public class ImageScanService {

    public boolean isImageSafe(String filePath) throws Exception {
        ByteString imgBytes = ByteString.readFrom(Files.newInputStream(Paths.get(filePath)));
        Image img = Image.newBuilder().setContent(imgBytes).build();

        Feature feature = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feature)
                .setImage(img)
                .build();

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(Collections.singletonList(request));
            AnnotateImageResponse annotateImageResponse = response.getResponsesList().get(0);

            if (annotateImageResponse.hasError()) {
                throw new Exception("Error: " + annotateImageResponse.getError().getMessage());
            }

            SafeSearchAnnotation annotation = annotateImageResponse.getSafeSearchAnnotation();
            return annotation.getAdult().getNumber() <= 2 && annotation.getViolence().getNumber() <= 2;
        }
    }
}

