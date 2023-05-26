package jjan_back_renewal.user.controller;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import jjan_back_renewal.user.dto.*;
import jjan_back_renewal.config.Response;
import jjan_back_renewal.user.service.UserService;
import jjan_back_renewal.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
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

    @Operation(summary = "회원가입", description = "회원가입 성공 후 Request 헤더의 Authorization 헤더에 토큰 값을 넣어줘야 합니다.")
    @PostMapping("/api/user/join")
    public ResponseEntity<JoinResponseDto> join(@RequestBody UserDto userDto) {
        JoinResponseDto joinResponseDto = userService.join(userDto);
        return ResponseEntity.ok().body(joinResponseDto);
    }

}
