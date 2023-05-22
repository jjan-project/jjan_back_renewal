package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.LoginRequestDto;
import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.exception.NoSuchNicknameException;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public static final Long NOT_DUPLICATED = -1L;

    @Override
    public UserDto login(LoginRequestDto loginRequestDto) {
        return null;
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
