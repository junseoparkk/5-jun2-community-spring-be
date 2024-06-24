package com.kcs.community.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ImageService {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    public String upload(MultipartFile image, String folder) {
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new IllegalArgumentException("image is empty");
        }
        return uploadImage(image, folder);
    }

    public void deleteImageFromS3(String imagePath) {
        String key = getKeyFromImageAddress(imagePath);
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
        } catch (S3Exception e) {
            throw new RuntimeException("Could not delete file from S3", e);
        }
    }

    private String uploadImage(MultipartFile image, String folder) {
        validateFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
        try {
            return uploadImageToS3(image, folder);
        } catch (IOException e) {
            throw new IllegalArgumentException("IOException on image upload");
        }
    }

    private void validateFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new IllegalArgumentException("Not valid extension");
        }

        String extension = fileName.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtensions.contains(extension)) {
            throw new IllegalArgumentException("Not valid extension");
        }
    }

    private String uploadImageToS3(MultipartFile image, String folder) throws IOException {
        String originalName = image.getOriginalFilename();
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalName;

        InputStream inputStream = image.getInputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readAllBytes());

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", image.getContentType());
        metadata.put("Content-Length", String.valueOf(image.getSize()));

        String s3Key = folder + "/" + s3FileName;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .metadata(metadata)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(byteArrayInputStream.readAllBytes()));
        } catch (S3Exception e) {
            throw new IOException("Could not upload file to S3", e);
        } finally {
            byteArrayInputStream.close();
            inputStream.close();
        }
        return String.format("https://%s.s3.%s.amazonaws.com/%s/%s", bucketName, region, folder, s3FileName);
    }
    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (IOException e) {
            throw new RuntimeException("Error while decoding image address", e);
        }
    }

}
