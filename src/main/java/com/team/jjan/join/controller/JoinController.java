package com.team.jjan.join.controller;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.common.dto.LogIn;
import com.team.jjan.join.dto.JoinRequest;
import com.team.jjan.join.dto.LoginRequest;
import com.team.jjan.join.dto.ValidationRequest;
import com.team.jjan.join.service.JoinService;
import com.team.jjan.join.service.RandomNicknameGenerateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class JoinController {

    private final JoinService joinService;
    private final RandomNicknameGenerateService randomNicknameGenerateService;

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
            throws AccountException {

        return ResponseEntity.ok().body(joinService.login(loginRequest , response));
    }

    @PostMapping(value = "/join")
    public ResponseEntity<ResponseMessage> join(@RequestPart(value = "data") JoinRequest joinRequest,
                                                @RequestPart(value = "image" , required = false) MultipartFile profileImage)
            throws IOException, AccountException {
        return ResponseEntity.ok().body(joinService.join(joinRequest , profileImage));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<ResponseMessage> logout(HttpServletResponse response) {
        return ResponseEntity.ok().body(joinService.logout(response));
    }

    @GetMapping("/random-nickname")
    public ResponseEntity<ResponseMessage> randomNickname() {
        return ResponseEntity.ok().body(randomNicknameGenerateService.generateRandomNickname());
    }

    @PostMapping("/unique-email")
    public ResponseEntity<ResponseMessage> isDuplicatedEmail(@RequestBody ValidationRequest request) {
        return ResponseEntity.ok().body(joinService.isDuplicateEmail(request.getData()));
    }

    @PostMapping("/unique-nickname")
    public ResponseEntity<ResponseMessage> isDuplicatedNickName(@RequestBody ValidationRequest request) {
        return ResponseEntity.ok().body(joinService.isDuplicateNickName(request.getData()));
    }

}
