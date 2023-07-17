package com.team.jjan.join.controller;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.join.dto.JoinRequest;
import com.team.jjan.join.dto.LoginRequest;
import com.team.jjan.join.dto.LoginResponse;
import com.team.jjan.join.service.JoinService;
import com.team.jjan.join.service.RandomNicknameGenerateService;
import com.team.jjan.user.dto.JoinResponse;
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

    /*
    @Operation(summary = "비밀번호 찾기", description = "사용자 인증(현재는 이메일,닉네임 인증) 후 이메일로 임시 비밀번호를 발송합니다")
    @PostMapping("/reset-password")
    public ResponseEntity<PasswordResponseDto> resetPassword(@RequestBody PasswordRequestDto passwordRequestDto) {
        PasswordResponseDto passwordResponseDto = joinService.resetPassword(passwordRequestDto);
        //reset failure
        if (passwordResponseDto.getEmail() == null)
            passwordResponseDto.response404();

        return ResponseEntity.ok().body(passwordResponseDto);
    }
    */

    @Operation(summary = "로그인", description = "로그인 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping("/login")
    public ResponseEntity<JoinResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
            throws AccountException {

        return ResponseEntity.ok().body(joinService.login(loginRequest , response));
    }

    @Operation(summary = "회원가입", description = "회원가입 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping(value = "/join" , consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseMessage> join(@RequestPart(value = "data") JoinRequest joinRequest,
                                                @RequestPart(value = "image" , required = false) MultipartFile profileImage) throws IOException {
        return ResponseEntity.ok().body(joinService.join(joinRequest , profileImage));
    }

    @Operation(summary = "랜덤 닉네임 생성", description = "유효한 랜덤 닉네임 생성 후 반환")
    @GetMapping("/random-nickname")
    public ResponseEntity<ResponseMessage> randomNickname() {
        return ResponseEntity.ok().body(randomNicknameGenerateService.generateRandomNickname());
    }

}
