package com.team.jjan.user.controller;

import com.team.jjan.jwt.support.JwtProvider;
import com.team.jjan.user.dto.JoinResponse;
import com.team.jjan.user.dto.setRequestDto;
import com.team.jjan.user.dto.setResponseDto;
import com.team.jjan.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    /*
    @Operation(summary = "주량 변경", description = "주량을 변경합니다")
    @PutMapping("/drink-capacity")
    public ResponseEntity<setResponseDto> setDrinkCapacity(HttpServletRequest request, @RequestBody setRequestDto setRequestDto) {
        String userEmail = jwtProvider.getUserEmail(request);
        String capacity = setRequestDto.getItem();
        JoinResponse joinResponse = userService.setDrinkCapacity(userEmail,capacity);
        return ResponseEntity.ok().body(new setResponseDto(joinResponse));
    }


    @Operation(summary = "닉네임 변경", description = "닉네임 중복 검사 이후 닉네임을 변경합니다")
    @PutMapping("/nickname")
    public ResponseEntity<setResponseDto> setNickName(HttpServletRequest request, @RequestBody setRequestDto setRequestDto) {
        String userEmail = jwtProvider.getUserEmail(request);
        String newNickName = setRequestDto.getItem();
        //닉네임 길이 및 중복 검사
        if ((isNickNameLengthOK(newNickName) 
                && userService.isDuplicatedNickName(newNickName) == UserService.NOT_DUPLICATED)
                && userService.isReplaceableUser(userEmail)) {
            JoinResponse joinResponse = userService.setNickName(userEmail,newNickName);
            return ResponseEntity.ok().body(new setResponseDto(joinResponse));
        } else {
            setResponseDto response = new setResponseDto();
            response.response403();
            return ResponseEntity.ok().body(response);
        }
    }

     */
}
