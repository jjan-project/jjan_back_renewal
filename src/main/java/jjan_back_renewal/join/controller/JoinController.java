package jjan_back_renewal.join.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jjan_back_renewal.join.dto.JoinResponseDto;
import jjan_back_renewal.join.dto.LoginRequestDto;
import jjan_back_renewal.join.dto.LoginResponseDto;
import jjan_back_renewal.join.dto.RandomNicknameGenerateResponseDto;
import jjan_back_renewal.join.service.RandomNicknameGenerateService;
import jakarta.servlet.http.HttpServletRequest;
import jjan_back_renewal.join.dto.*;
import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.join.service.JoinService;
import jjan_back_renewal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class JoinController {

    private final JoinService joinService;
    private final RandomNicknameGenerateService randomNicknameGenerateService;

    @Operation(summary = "비밀번호 찾기", description = "사용자 인증(현재는 이메일,한글성명 인증) 후 이메일로 임시 비밀번호를 발송합니다")
    @PostMapping("/reset-password")
    public ResponseEntity<PasswordResponseDto> resetPassword(@RequestBody PasswordRequestDto passwordRequestDto) {
        PasswordResponseDto passwordResponseDto = joinService.resetPassword(passwordRequestDto);
        //reset failure
        if(passwordResponseDto.getEmail() == null)
            passwordResponseDto.response404();

        return ResponseEntity.ok().body(passwordResponseDto);
    }

    @Operation(summary = "로그인", description = "로그인 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = joinService.login(loginRequestDto);
        // login failure
        if (loginResponseDto.getUserDto() == null) {
            loginResponseDto.response403();
            return ResponseEntity.ok().body(loginResponseDto);
        }
        setRefreshTokenInCookie(response, loginResponseDto.getToken().getRefreshToken());
        return ResponseEntity.ok().body(loginResponseDto);
    }

    @Operation(summary = "회원가입", description = "회원가입 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping("/join")
    public ResponseEntity<JoinResponseDto> join(@RequestBody UserDto userDto, HttpServletResponse response) {
        JoinResponseDto joinResponseDto = joinService.join(userDto);
        setRefreshTokenInCookie(response, joinResponseDto.getToken().getRefreshToken());
        return ResponseEntity.ok().body(joinResponseDto);
    }

    @Operation(summary = "랜덤 닉네임 생성", description = "유효한 랜덤 닉네임 생성 후 반환")
    @GetMapping("/random-nickname")
    public ResponseEntity<RandomNicknameGenerateResponseDto> randomNickname() {
        return ResponseEntity.ok()
                .body(new RandomNicknameGenerateResponseDto(
                        randomNicknameGenerateService.generateRandomNickname(8))
                );
    }
 
    private void setRefreshTokenInCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("refresh_token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 90);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
