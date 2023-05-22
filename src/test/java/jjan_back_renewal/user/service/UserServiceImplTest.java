package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("DB 에 없는 이메일을 findByEmail 호출시 NoSuchEmailException 이 발생한다.")
    void findByEmail_없는이메일_exception() {
        assertThatThrownBy(() -> {
            userService.findByEmail("nonono@email.com");
        }).isExactlyInstanceOf(NoSuchEmailException.class);
    }

    @Test
    @DisplayName("DB 에 있는 이메일을 findByEmail 호출하면 정상적으로 해당 UserEntity id 를 반환한다.")
    void findByEmail_있는이메일() {
        String email = "test@naver.com";
        UserEntity save = saveEntityByEmail(email);
        UserDto byEmail = userService.findByEmail(email);
        assertThat(byEmail.getEmail()).isEqualTo(email);
        userRepository.deleteById(save.getId());
    }

    @Test
    @DisplayName("DB 에 없는 이메일에 isDuplicatedEmail 호출하면 NOT_DUPLICATED 값을 반환하지 않는다.")
    void isDuplicatedEmail_있는이메일_returns_DUPLICATED() {
        String email = "test@naver.com";
        UserEntity save = saveEntityByEmail(email);
        assertThat(userService.isDuplicatedEmail(email)).isNotEqualTo(UserServiceImpl.NOT_DUPLICATED);
        userRepository.deleteById(save.getId());
    }

    @Test
    @DisplayName("DB 에 없는 이메일에 isDuplicatedEmail 호출하면 NOT_DUPLICATED 값을 반환한다.")
    void isDuplicatedEmail_없는이메일_returns_NOT_DUPLICATED() {
        String email = "test@naver.com";
        assertThat(userService.isDuplicatedEmail(email)).isEqualTo(UserServiceImpl.NOT_DUPLICATED);
    }

    UserEntity saveEntityByEmail(String email) {
        return userRepository.save(UserEntity.builder()
                .email(email)
                .nickName("nickname")
                .sex("M")
                .birth("19980830")
                .password("pwd")
                .address("SEOUL")
                .build());
    }
}