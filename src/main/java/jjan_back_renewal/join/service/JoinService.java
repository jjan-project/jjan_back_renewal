package jjan_back_renewal.join.service;

import jjan_back_renewal.join.dto.*;
import jjan_back_renewal.user.dto.UserDto;

public interface JoinService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    JoinResponseDto join(UserDto userDto);

    PasswordResponseDto resetPassword(PasswordRequestDto passwordRequestDto);
}
