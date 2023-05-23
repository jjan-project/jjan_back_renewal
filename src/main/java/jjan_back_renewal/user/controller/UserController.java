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
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/unique-email")
    public ResponseEntity<UniqueTestResponseDto> isDuplicatedEmail(@RequestBody String email) {
        if (isEmail(email) && userService.isDuplicatedEmail(email) == UserServiceImpl.NOT_DUPLICATED) {
            return ResponseEntity.ok().body(new UniqueTestResponseDto("email", email));
        } else {
            UniqueTestResponseDto response = new UniqueTestResponseDto("email", email);
            response.response403();
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("/unique-nickname")
    public ResponseEntity<UniqueTestResponseDto> isDuplicatedNickName(@RequestBody String nickName) {

        if (isNickNameLengthOK(nickName) && userService.isDuplicatedNickName(nickName) == UserServiceImpl.NOT_DUPLICATED) {
            return ResponseEntity.ok().body(new UniqueTestResponseDto("nickName", nickName));
        } else {
            UniqueTestResponseDto uniqueTestResponseDto = new UniqueTestResponseDto("nickName", nickName);
            uniqueTestResponseDto.response403();
            return ResponseEntity.ok().body(uniqueTestResponseDto);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        // login failure
        if (loginResponseDto.getUserDto() == null) {
            loginResponseDto.response403();
            return ResponseEntity.ok().body(loginResponseDto);
        }
        return ResponseEntity.ok().body(loginResponseDto);
    }


    private boolean isNickNameLengthOK(String nickName) {
        if (nickName.length() >= 8 && nickName.length() <= 16) {
            return true;
        } else
            return false;
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
