package com.team.jjan.upload;

import com.team.jjan.jwt.support.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtProvider jwtProvider;

    @PostMapping("/set-profile-image")
    public ResponseEntity<FileUploadResponse> uploadProfileImage(HttpServletRequest request,
                                                                 @RequestParam("profileImage") MultipartFile multipartFile) throws IOException {
        String email = jwtProvider.getUserEmail(request);
        FileUploadResponse profileUploadResponse = fileUploadService.uploadProfileImage(email, multipartFile);
        return ResponseEntity.ok(profileUploadResponse);
    }
}
