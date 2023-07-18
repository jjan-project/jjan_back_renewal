package com.team.jjan.join.controller;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.join.dto.JoinRequest;
import com.team.jjan.join.dto.LoginRequest;
import com.team.jjan.join.dto.ValidationRequest;
import com.team.jjan.join.service.JoinService;
import com.team.jjan.join.service.RandomNicknameGenerateService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "로그인", description = "로그인 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
            throws AccountException {

        return ResponseEntity.ok().body(joinService.login(loginRequest , response));
    }

    @Operation(summary = "회원가입", description = "회원가입 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping(value = "/join" , consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseMessage> join(@RequestPart(value = "data") JoinRequest joinRequest,
                                                @RequestPart(value = "image" , required = false) MultipartFile profileImage)
            throws IOException, AccountException {
        return ResponseEntity.ok().body(joinService.join(joinRequest , profileImage));
    }

    @Operation(summary = "랜덤 닉네임 생성", description = "유효한 랜덤 닉네임 생성 후 반환")
    @GetMapping("/random-nickname")
    public ResponseEntity<ResponseMessage> randomNickname() {
        return ResponseEntity.ok().body(randomNicknameGenerateService.generateRandomNickname());
    }

    @Operation(summary = "이메일 중복 체크")
    @PostMapping("/unique-email")
    public ResponseEntity<ResponseMessage> isDuplicatedEmail(@RequestBody ValidationRequest request) {
        return ResponseEntity.ok().body(joinService.isDuplicateEmail(request.getData()));
    }

    @Operation(summary = "닉네임 중복 체크", description = "중복 닉네임일 시 403 status code 반환합니다.")
    @PostMapping("/unique-nickname")
    public ResponseEntity<ResponseMessage> isDuplicatedNickName(@RequestBody ValidationRequest request) {
        return ResponseEntity.ok().body(joinService.isDuplicateNickName(request.getData()));
    }

}
