package jjan_back_renewal.user.controller;

import io.micrometer.common.util.StringUtils;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @GetMapping("/")
    public String root() {
        return "exampleMapping";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    //이메일 중복검증, 수정필요
    @GetMapping("/userEmail/{userEmail}")
    public Response<?> findUserByEmail(@PathVariable("userEmail") String userEmail) throws Exception {
        return new Response<>("true", "조회 성공", userService.findByEmail(userEmail));
    }

    //닉네임 중복검증, 수정필요
    @GetMapping("/userNickName/{userNickName}")
    public Response<?> findUserByNickName(@PathVariable("userNickName") String userNickName) throws Exception {
        return new Response<>("true", "조회 성공", userService.findByNickName(userNickName));
    }

    @PostMapping("/api/user/unique-email")
    public ResponseEntity<UniqueTestResponseDto> isDuplicatedEmail(@RequestBody String email) {
        if (isEmail(email) && userService.isDuplicatedEmail(email) == UserServiceImpl.NOT_DUPLICATED) {
            return ResponseEntity.ok().body(new UniqueTestResponseDto("email", email));
        } else {
            UniqueTestResponseDto response = new UniqueTestResponseDto("email", email);
            response.response403();
            return ResponseEntity.ok().body(response);
        }
    }

    private boolean isEmail(String email) {
        boolean validation = false;
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            validation = true;
        }
        return validation;
    }
}
