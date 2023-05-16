package jjan_back_renewal.user.controller;

import jjan_back_renewal.config.Response;
import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/")
    public String root(){
        return "exampleMapping";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    //이메일 중복검증, 수정필요
    @GetMapping("/userEmail/{userEmail}")
    public Response<?> findUserByEmail(@PathVariable("userEmail") String userEmail) throws Exception {
        return new Response<>("true","조회 성공",userService.findByEmail(userEmail));
    }

    //닉네임 중복검증, 수정필요
    @GetMapping("/userNickName/{userNickName}")
    public Response<?> findUserByNickName(@PathVariable("userNickName") String userNickName) throws Exception {
        return new Response<>("true","조회 성공",userService.findByNickName(userNickName));
    }
}
