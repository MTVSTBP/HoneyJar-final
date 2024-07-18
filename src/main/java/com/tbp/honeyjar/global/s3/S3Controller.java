package com.tbp.honeyjar.global.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/pre-signed-url/image/{fileName}")
    public ResponseEntity<Map<String, String>> getPreSignedUrl(@PathVariable String fileName) {
        try {
            String bucketName = "honeyjar-bucket";
            Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 5); // 5분

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, "images/" + fileName)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expiration);

            URL url = s3Service.generatePresignedUrl(generatePresignedUrlRequest);

            Map<String, String> response = new HashMap<>();
            response.put("presignedUrl", url.toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}

