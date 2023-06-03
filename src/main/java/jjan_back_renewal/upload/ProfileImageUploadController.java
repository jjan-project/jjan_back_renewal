package jjan_back_renewal.upload;

import jakarta.servlet.http.HttpServletRequest;
import jjan_back_renewal.join.auth.JwtProvider;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ProfileImageUploadController {
    private final FileUploadService fileUploadService;
    private final JwtProvider jwtProvider;

    @PostMapping("/set-profile-image")
    public ResponseEntity<FileUploadResponseDto> uploadProfileImage(HttpServletRequest request,
                                                                    @RequestParam("profilePhoto") MultipartFile multipartFile) throws IOException {
        String email = jwtProvider.getUserEmail(request);
        FileUploadResponseDto profileUploadResponse = fileUploadService.uploadProfileImage(email, multipartFile);
        return ResponseEntity.ok(profileUploadResponse);
    }
}
