package com.team.jjan.upload.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team.jjan.upload.dto.FileUploadResponse;
import com.team.jjan.upload.exception.FileUploadException;
import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadService {

    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;
    private static final String PROFILE_IMAGE_DIR_NAME = "profile";
    private static final String PARTY_IMAGE_DIR_NAME = "party";
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFileToS3(MultipartFile file , String uuid) throws IOException {
        if(!isValidFile(file)) {
            throw new FileUploadException("올바르지 않은 파일입니다.");
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getInputStream().available());
        amazonS3Client.putObject(bucket , uuid ,file.getInputStream() , metadata);

        return amazonS3Client.getUrl(bucket , uuid).toString();
    }

    public void deleteFile(String fileName) {
        boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, fileName);

        if(isObjectExist) {
            amazonS3Client.deleteObject(bucket, fileName);
        }
    }

    public boolean isValidFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("bmp") || extension.equals("svg")) {
            return true;
        }

        return false;
    }

}