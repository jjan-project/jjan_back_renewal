package com.team.jjan.user.service;

import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.exception.NoSuchNicknameException;
import com.team.jjan.user.repository.UserRepository;
import com.team.jjan.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public static final Long NOT_DUPLICATED = -1L;

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

    @Override
    @Transactional
    public UserDto setNickName(String email, String nickName) {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        targetUser.setNickName(nickName);
        targetUser.setNickNameChangeAvailable(false);

        return new UserDto(targetUser);
    }

    @Override
    @Transactional
    public UserDto setDrinkCapacity(String email, String capacity) {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        targetUser.setDrinkCapacity(capacity);
        return new UserDto(targetUser);
    }

    @Override
    public boolean isReplaceableUser(String email) {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        return targetUser.isNickNameChangeAvailable();
    }
}
