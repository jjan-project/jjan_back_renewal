package com.team.jjan.join.service;

import com.team.jjan.join.dto.*;
import com.team.jjan.user.dto.UserDto;

public interface JoinService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    JoinResponseDto join(UserDto userDto);

    PasswordResponseDto resetPassword(PasswordRequestDto passwordRequestDto);
}
