package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.LoginRequestDto;
import jjan_back_renewal.user.dto.LoginResponseDto;
import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.exception.NoSuchNicknameException;
import jjan_back_renewal.user.join.JwtProvider;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    public static final Long NOT_DUPLICATED = -1L;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        UserEntity userEntity = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Wrong Authentication"));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("Wrong Authentication");
        }
        return new LoginResponseDto(new UserDto(userEntity), jwtProvider.createToken(userEntity.getEmail(), userEntity.getRoles()));
    }

    //client에게 dto로 정보를 입력 받고
    //Entity에 Setter 없이 Mapper를 통해 DTO로 client -> Controller -> Entity로 값을 넘긴다
    @Override
    public UserDto register(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        //. . . . .
        return new UserDto(userEntity);
    }

    @Override
    public UserDto findByEmail(String email) {
        UserEntity byEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        return new UserDto(byEmail);
    }

    @Override
    public UserDto findByNickName(String nickName) {
        UserEntity byNickName = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchNicknameException(nickName));
        return new UserDto(byNickName);
    }

    @Override
    public Long isDuplicatedNickName(String nickName) {
        Long ret = NOT_DUPLICATED;
        try {
            UserEntity userEntity = userRepository.findByNickName(nickName)
                    .orElseThrow(() -> new NoSuchNicknameException(nickName));
            ret = userEntity.getId();
        } catch (NoSuchNicknameException e) {
            return ret;
        }
        return ret;
    }

    @Override
    public Long isDuplicatedEmail(String email) {
        Long ret = NOT_DUPLICATED;
        try {
            UserEntity userEntity = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NoSuchEmailException(email));
            ret = userEntity.getId();
        } catch (NoSuchEmailException e) {
            return ret;
        }
        return ret;
    }

}
