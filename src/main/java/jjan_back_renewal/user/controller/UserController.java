package jjan_back_renewal.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jjan_back_renewal.user.dto.LoginRequestDto;
import jjan_back_renewal.user.dto.LoginResponseDto;
import jjan_back_renewal.config.Response;
import jjan_back_renewal.user.dto.UniqueTestResponseDto;
import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.service.UserService;
import jjan_back_renewal.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "error test", description = "DB에 없는 이메일 검색 시 에러 핸들링")
    @GetMapping("/error-test")
    public String error() {
        userService.findByEmail("hello@naver.com");
        return "hello";
    }

    @GetMapping("/login-test")
    public ResponseEntity<LoginResponseDto> login() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("이름", "비번");
        UserDto userDto = userService.login(loginRequestDto);
        LoginResponseDto loginResponseDto = new LoginResponseDto(userDto);
        // login failure
        if (loginResponseDto.getUserDto() == null) {
            loginResponseDto.response403();
            return ResponseEntity.ok().body(loginResponseDto);
        }
        return ResponseEntity.ok().body(loginResponseDto);
    }

    //이메일로 유저 찾기
    @GetMapping("/userEmail/{userEmail}")
    public Response<?> findUserByEmail(@PathVariable("userEmail") String userEmail) throws Exception {
        return new Response<>("true", "조회 성공", userService.findByEmail(userEmail));
    }
    
    //닉네임으로 유저 찾기
    @GetMapping("/userNickName/{userNickName}")
    public Response<?> findUserByNickName(@PathVariable("userNickName") String userNickName) throws Exception {
        return new Response<>("true", "조회 성공", userService.findByNickName(userNickName));
    }

    //닉네임 중복검증
    //요청한 DTO가 null이 아닐경우 중복
    @PostMapping("/api/user/unique-nickname")
    public ResponseEntity<UniqueTestResponseDto> isDuplicatedNickName(@RequestBody String nickName) {

        if(isNickNameLengthOK(nickName) && userService.isDuplicatedNickName(nickName) == UserServiceImpl.NOT_DUPLICATED) {
            return ResponseEntity.ok().body(new UniqueTestResponseDto("nickName",nickName));
        }
        else {
            UniqueTestResponseDto uniqueTestResponseDto = new UniqueTestResponseDto("nickName",nickName);
            uniqueTestResponseDto.response403();
            return ResponseEntity.ok().body(uniqueTestResponseDto);
        }

    }

    private boolean isNickNameLengthOK(String nickName) {
        if(nickName.length() >= 8  && nickName.length() <= 16) {
            return true;
        }
        else
            return false;
    }

}
