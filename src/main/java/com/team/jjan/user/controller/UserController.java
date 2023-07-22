package com.team.jjan.user.controller;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.common.dto.LogIn;
import com.team.jjan.user.dto.RequestData;
import com.team.jjan.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "주량 변경", description = "주량을 변경합니다")
    @PatchMapping("/drink-capacity")
    public ResponseEntity<ResponseMessage> setDrinkCapacity(HttpServletRequest request, @RequestBody RequestData requestData) {
        return ResponseEntity.ok().body(userService.setDrinkCapacity(request , requestData));
    }


    @Operation(summary = "닉네임 변경", description = "닉네임 중복 검사 이후 닉네임을 변경합니다")
    @PatchMapping("/nickname")
    public ResponseEntity<ResponseMessage> setNickName(HttpServletRequest request, @RequestBody RequestData requestData) throws AccountException {
        return ResponseEntity.ok().body(userService.setNickName(request , requestData));
    }

    @Operation(summary = "사용자 이메일로 사용자 제거")
    @DeleteMapping("/{userEmail}")
    public ResponseEntity deleteUserByEmail(@PathVariable String userEmail) {
        return ResponseEntity.ok().body(userService.deleteUserByEmail(userEmail));
    }

    @Operation(summary = "AccessToken을 이용하여 사용자 제거")
    @DeleteMapping
    public ResponseEntity deleteUserByToken(HttpServletRequest request , HttpServletResponse response) {
        return ResponseEntity.ok().body(userService.deleteUser(request , response));
    }

    @Operation(summary = "프로필 이미지 변경")
    @PatchMapping("/profile-image")
    public ResponseEntity<ResponseMessage> setProfileImage(HttpServletRequest request, @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok().body(userService.setProfileImage(request , multipartFile));
    }
}
