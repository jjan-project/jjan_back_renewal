package com.team.jjan.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

    public List<FileUploadResponse> uploadPartyImages(String puuid, List<MultipartFile> partyImages) throws IOException {
        List<FileUploadResponse> responseDtoList = new ArrayList<>();
        for (int i = 0; i < partyImages.size(); i++) {
            responseDtoList.add(uploadPartyImage(puuid + "_" + i, partyImages.get(i)));
        }
        return responseDtoList;
    }

    public FileUploadResponse uploadPartyImage(String imageName, MultipartFile imageFile) throws IOException {
        FileUploadResponse fileUploadResponse = uploadPartyImageS3(imageName, imageFile);
        return fileUploadResponse;
    }

    public FileUploadResponse uploadProfileImage(String userEmail, MultipartFile multipartFile) throws IOException {
        FileUploadResponse fileUploadResponse = uploadProfileImageS3(userEmail, multipartFile);
        updateProfileImage(fileUploadResponse);
        return fileUploadResponse;
    }

    public FileUploadResponse uploadPartyImageS3(String imageName, MultipartFile multipartFile) throws IOException {
        return upload(imageName, multipartFile, PARTY_IMAGE_DIR_NAME);
    }

    public FileUploadResponse uploadProfileImageS3(String userEmail, MultipartFile multipartFile) throws IOException {
        return upload(userEmail, multipartFile, PROFILE_IMAGE_DIR_NAME);
    }

    @Transactional
    public void updateProfileImage(FileUploadResponse upload) {
        UserEntity user = userRepository.findByEmail(upload.getIdentifier())
                .orElseThrow(() -> new FileUploadException("해당하는 유저 " + upload.getIdentifier() + "를 찾을 수 없습니다"));
        user.setProfile(upload.getUrl());
    }

    private FileUploadResponse upload(String identifier, MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new FileUploadException("MultipartFile -> File 전환이 실패했습니다."));

        return upload(identifier, uploadFile, dirName);
    }

    private FileUploadResponse upload(String identifier, File uploadFile, String dirName) {
        String fileName = dirName + "/" + identifier;
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return new FileUploadResponse(identifier, fileName, uploadImageUrl);
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        String encodedFileName = new String(file.getOriginalFilename().getBytes("UTF-8"), "8859_1");
        File convertFile = new File(encodedFileName);

        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}