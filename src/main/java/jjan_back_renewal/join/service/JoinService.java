package jjan_back_renewal.join.service;

import jjan_back_renewal.join.dto.JoinResponseDto;
import jjan_back_renewal.join.dto.LoginRequestDto;
import jjan_back_renewal.join.dto.LoginResponseDto;
import jjan_back_renewal.user.dto.UserDto;

public interface JoinService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    JoinResponseDto join(UserDto userDto);
}
