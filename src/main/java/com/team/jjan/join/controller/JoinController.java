package com.team.jjan.join.controller;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.join.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import com.team.jjan.join.service.RandomNicknameGenerateService;
import com.team.jjan.user.dto.JoinResponse;
import com.team.jjan.join.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.team.jjan.jwt.support.JwtCookie.setRefreshTokenInCookie;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class JoinController {

    private final JoinService joinService;
    private final RandomNicknameGenerateService randomNicknameGenerateService;

    @Operation(summary = "비밀번호 찾기", description = "사용자 인증(현재는 이메일,닉네임 인증) 후 이메일로 임시 비밀번호를 발송합니다")
    @PostMapping("/reset-password")
    public ResponseEntity<PasswordResponseDto> resetPassword(@RequestBody PasswordRequestDto passwordRequestDto) {
        PasswordResponseDto passwordResponseDto = joinService.resetPassword(passwordRequestDto);
        //reset failure
        if (passwordResponseDto.getEmail() == null)
            passwordResponseDto.response404();

        return ResponseEntity.ok().body(passwordResponseDto);
    }

    @Operation(summary = "로그인", description = "로그인 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = joinService.login(loginRequest);
        // login failure
        if (loginResponse.getJoinResponse() == null) {
            loginResponse.response403();
            return ResponseEntity.ok().body(loginResponse);
        }
        setRefreshTokenInCookie(response, loginResponse.getToken().getRefreshToken());

        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(summary = "회원가입", description = "회원가입 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping("/join")
    public ResponseEntity<ResponseMessage> join(@RequestBody JoinRequest joinRequest, @RequestParam MultipartFile profileImage) throws IOException {
        return ResponseEntity.ok().body(joinService.join(joinRequest , profileImage));
    }

    @Operation(summary = "랜덤 닉네임 생성", description = "유효한 랜덤 닉네임 생성 후 반환")
    @GetMapping("/random-nickname")
    public ResponseEntity<RandomNicknameGenerateResponseDto> randomNickname() {
        return ResponseEntity.ok()
                .body(new RandomNicknameGenerateResponseDto(
                        randomNicknameGenerateService.generateRandomNickname(8))
                );
    }


}
