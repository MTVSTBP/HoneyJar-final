package com.tbp.honeyjar.global.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final String MISSING_IMAGE_EXTENSION_EXCEPTION_MESSAGE = "확장자가 누락되었습니다.";
    private static final String INVALID_IMAGE_EXTENSION_EXCEPTION_MESSAGE = "jpg, jpeg, png, gif 확장자만을 지원합니다.";
    private static final String IMAGE_URL_PATH_FORMAT = "%s/%s";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public URL generatePresignedUrl(GeneratePresignedUrlRequest generatePresignedUrlRequest) {
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public String getPresignedUrl(String prefix, String fileName) {
        validateFileName(fileName);

        if (!prefix.isEmpty()) {
            fileName = createPath(prefix, fileName);
        }

        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(bucket, fileName);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

    public void uploadFile(File file) {
        String fileName = file.getName();
        validateFileName(fileName);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, "images/" + fileName, file);
        amazonS3.putObject(putObjectRequest);
    }

    private void validateFileName(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (extension.equals(fileName)) {
            throw new IllegalArgumentException(MISSING_IMAGE_EXTENSION_EXCEPTION_MESSAGE);
        }

        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException(INVALID_IMAGE_EXTENSION_EXCEPTION_MESSAGE);
        }
    }

    private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String bucket, String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getPresignedUrlExpiration());

        return generatePresignedUrlRequest;
    }

    private Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);

        return expiration;
    }

    private String createPath(String prefix, String fileName) {
        String fileId = createFileId();
        return String.format(IMAGE_URL_PATH_FORMAT, prefix, fileId + "-" + fileName);
    }

    private String createFileId() {
        return UUID.randomUUID().toString();
    }
}