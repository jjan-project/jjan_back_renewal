package jjan_back_renewal.join.controller;

import io.swagger.v3.oas.annotations.Operation;
import jjan_back_renewal.join.dto.JoinResponseDto;
import jjan_back_renewal.join.dto.LoginRequestDto;
import jjan_back_renewal.join.dto.LoginResponseDto;
import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.join.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class JoinController {

    private final JoinService joinService;

    @Operation(summary = "로그인", description = "로그인 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = joinService.login(loginRequestDto);
        // login failure
        if (loginResponseDto.getUserDto() == null) {
            loginResponseDto.response403();
            return ResponseEntity.ok().body(loginResponseDto);
        }
        return ResponseEntity.ok().body(loginResponseDto);
    }

    @Operation(summary = "회원가입", description = "회원가입 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping("/join")
    public ResponseEntity<JoinResponseDto> join(@RequestBody UserDto userDto) {
        JoinResponseDto joinResponseDto = joinService.join(userDto);
        return ResponseEntity.ok().body(joinResponseDto);
    }

}