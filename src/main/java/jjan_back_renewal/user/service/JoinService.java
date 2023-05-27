package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.JoinResponseDto;
import jjan_back_renewal.user.dto.LoginRequestDto;
import jjan_back_renewal.user.dto.LoginResponseDto;
import jjan_back_renewal.user.dto.UserDto;

public interface JoinService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    JoinResponseDto join(UserDto userDto);
}
