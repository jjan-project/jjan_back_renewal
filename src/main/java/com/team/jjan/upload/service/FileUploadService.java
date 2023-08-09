package com.team.jjan.upload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.team.jjan.upload.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadService {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFileToS3(MultipartFile file , String uuid) throws IOException {
        if(!isValidFile(file)) {
            throw new FileUploadException("올바르지 않은 파일입니다.");
        }

        amazonS3Client.putObject(bucket , uuid ,file.getInputStream() , createMetadata(file));

        return amazonS3Client.getUrl(bucket , uuid).toString();
    }

    public ObjectMetadata createMetadata(MultipartFile multipartFile) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getInputStream().available());

        return metadata;
    }

    public void deleteFile(String fileName) {
        if(fileName.equals("blank")) {
            return;
        }

        String fileKey[] = fileName.split("/");
        int length = fileKey.length - 1;

        if (amazonS3Client.doesObjectExist(bucket, fileKey[length])) {
            amazonS3Client.deleteObject(bucket, fileKey[length]);
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