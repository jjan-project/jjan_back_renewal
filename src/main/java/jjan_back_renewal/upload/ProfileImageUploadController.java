package jjan_back_renewal.upload;

import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ProfileImageUploadController {
    private final FileUploadService fileUploadService;
    private final UserRepository userRepository;

    //유저 프로필 업로드
    @PostMapping("/set-profile-image")
    public ResponseEntity<FileUploadResponseDto> uploadProfileImage(
            @RequestParam("profilePhoto") MultipartFile multipartFile) throws IOException {
        UserEntity user = saveEntityByEmail("test@naver.com");
        FileUploadResponseDto profileUploadResponse = fileUploadService.uploadProfileImage(user.getEmail(), multipartFile);
        return ResponseEntity.ok(profileUploadResponse);
    }

    UserEntity saveEntityByEmail(String email) {
        return userRepository.save(UserEntity.builder()
                .email(email)
                .nickName("nickname")
                .name("testname")
                .gender("M")
                .birth("19980830")
                .password("pwd")
                .address("SEOUL")
                .profile("")
                .build());
    }
}
