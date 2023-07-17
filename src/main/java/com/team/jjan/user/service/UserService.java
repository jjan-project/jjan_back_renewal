package com.team.jjan.user.service;

import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.exception.NoSuchNicknameException;
import com.team.jjan.user.repository.UserRepository;
import com.team.jjan.user.dto.JoinResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public JoinResponse findByEmail(String email) {
        UserEntity byEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        return new JoinResponse(byEmail);
    }

    public JoinResponse findByNickName(String nickName) {
        UserEntity byNickName = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchNicknameException(nickName));
        return new JoinResponse(byNickName);
    }

    @Transactional
    public JoinResponse setNickName(String email, String nickName) {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        targetUser.setNickName(nickName);
        targetUser.setNickNameChangeAvailable(false);

        return new JoinResponse(targetUser);
    }

    @Transactional
    public JoinResponse setDrinkCapacity(String email, String capacity) {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        targetUser.setDrinkCapacity(capacity);
        return new JoinResponse(targetUser);
    }

    public boolean isReplaceableUser(String email) {
        UserEntity targetUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException(email));
        return targetUser.isNickNameChangeAvailable();
    }
}
