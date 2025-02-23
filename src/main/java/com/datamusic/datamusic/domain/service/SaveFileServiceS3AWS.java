package com.datamusic.datamusic.domain.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.datamusic.datamusic.persistence.DTO.FileAwsResponse;
import com.datamusic.datamusic.persistence.DTO.FileUploadResponse;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class SaveFileServiceS3AWS {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    private AmazonS3 s3Client;

    @Autowired
    private Environment environment;

    // methods for AWS
    @PostConstruct
    private void initialize() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public S3Object getFileFromS3(String s3Key) {
        System.out.println(s3Key);
        String filePath = "";
        String[] activeProfiles = environment.getActiveProfiles();
        if (containsProfile(activeProfiles, "dev")) {
            filePath = "dev" +s3Key;
        } else if (containsProfile(activeProfiles, "prod")) {
            filePath = "prod" +s3Key;
        }
        return s3Client.getObject(bucketName, filePath);
    }

    public FileUploadResponse uploadFile(MultipartFile multipartFile, String pathToSave) {
        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        String filePath = "";
        String[] activeProfiles = environment.getActiveProfiles();
        if (containsProfile(activeProfiles, "dev")) {
            filePath = "dev" + pathToSave + "/" + multipartFile.getOriginalFilename();
        } else if (containsProfile(activeProfiles, "prod")) {
            filePath = "prod" + pathToSave + "/" + multipartFile.getOriginalFilename();
        }
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());
            s3Client.putObject(bucketName, filePath, multipartFile.getInputStream(), objectMetadata);
            String urlFileInBucket = s3Client.getUrl(bucketName, filePath).toString();
            fileUploadResponse.setFilePath(generatePresignedUrl(filePath));
            fileUploadResponse.setNameFile(multipartFile.getOriginalFilename());
            fileUploadResponse.setDateTime(LocalDateTime.now());
        } catch (IOException e) {
            System.out.println("Error occurred in file upload ==> " + e.getMessage());
        }
        return fileUploadResponse;
    }

    public FileUploadResponse uploadThumbNailS3(ByteArrayOutputStream imageStream, String pathToSave) {
        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        String[] activeProfiles = environment.getActiveProfiles();
        String filePath = "";
        String uniqueFileName = UUID.randomUUID().toString() + "_thumbnail.jpg";
        if (containsProfile(activeProfiles, "dev")) {
            filePath = "dev" + pathToSave;
        } else if (containsProfile(activeProfiles, "prod")) {
            filePath = "prod" + pathToSave;
        }
        // Subir el thumbnail a S3
        byte[] thumbnailData = imageStream.toByteArray();
        ObjectMetadata thumbnailMetadata = new ObjectMetadata();
        thumbnailMetadata.setContentLength(thumbnailData.length);
        s3Client.putObject(new PutObjectRequest(bucketName, filePath,
                new ByteArrayInputStream(thumbnailData), thumbnailMetadata));

        fileUploadResponse.setFilePath(generatePresignedUrl(filePath));
        fileUploadResponse.setNameFile(uniqueFileName);
        fileUploadResponse.setDateTime(LocalDateTime.now());
        return fileUploadResponse;

    }

    public FileUploadResponse getFile(String pathToSave, String FileName) {
        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        String filePath = "";
        String[] activeProfiles = environment.getActiveProfiles();
        if (containsProfile(activeProfiles, "dev")) {
            filePath = "dev" + pathToSave + "/" + FileName;
        } else if (containsProfile(activeProfiles, "prod")) {
            filePath = "prod" + pathToSave + "/" + FileName;
        }
        fileUploadResponse.setFilePath(generatePresignedUrl(filePath));
        fileUploadResponse.setNameFile(FileName);
        fileUploadResponse.setDateTime(LocalDateTime.now());
        return fileUploadResponse;
    }

    public String generatePresignedUrl(String objectKey) {
        // System.out.println(objectKey);
        // // Validate that the file exists in the bucket
        // if (!doesFileExist(objectKey)) {
        // throw new IllegalArgumentException("File not found in S3 bucket: " +
        // objectKey);
        // }
        // Set expiration for the URL (e.g., 1 hour)
        try {
            boolean doesItExists = s3Client.doesObjectExist(bucketName, objectKey);
        } catch (Exception error) {
            return "";
        }

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1 hour
        expiration.setTime(expTimeMillis);

        // Generate the pre-signed URL
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    // public FileAwsResponse getDataFile(String path){
    // S3Object file= s3Client.getObject(bucketName,path);
    // InputStream objectData = file
    // .getObjectContent();
    // // Process the objectData stream.
    // objectData.close();
    // return
    // }

    private boolean containsProfile(String[] profiles, String profileToCheck) {
        for (String profile : profiles) {
            if (profile.equalsIgnoreCase(profileToCheck)) {
                return true;
            }
        }
        return false;
    }
}
