package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.exception.NoSuchNicknameException;
import jjan_back_renewal.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


//    @BeforeEach
//    void beforeAll() {
//        String nickName = "ExistUser1";
//        userRepository.save(UserEntity.builder()
//                .email("")
//                .nickName(nickName)
//                .password("")
//                .profile("")
//                .name("")
//                .address("")
//                .gender("")
//                .birth("")
//                .build());
//    }


    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("DB에 없는 nickName을 호출 시 NoSuchNickNameException이 발생한다")
    void findByNickName_notInDB() {
        assertThatThrownBy(() -> {
            userService.findByNickName("NotExistUser1");
        }).isExactlyInstanceOf(NoSuchNicknameException.class);
    }

    @Test
    @DisplayName("DB에 있는 nickName을 호출 시 UserEntity의 id를 반환한다")
    void findByNickName_InDB() {
        String nickName = "ExistUser1";
        UserEntity exist = saveEntityByNickName(nickName);
        UserDto newUser = userService.findByNickName(nickName);
        assertThat(newUser.getId()).isEqualTo(exist.getId());
    }

    @Test
    @DisplayName("DB에 있는 NickName에 isDuplicatedNickName을 호출하면 NOT_DUPLICATED 값을 반환하지 않는다. ")
    void isDuplicatedNickName_Duplicated() {
        String nickName = "ExistUser1";
        saveEntityByNickName(nickName);
        assertThat(userService.isDuplicatedNickName(nickName)).isNotEqualTo(UserServiceImpl.NOT_DUPLICATED);
    }


    @Test
    @DisplayName("DB에 없는 NickName에 isDuplicatedNickName을 호출하면 NOT_DUPLICATED 값을 반환한다. ")
    void isDuplicatedNickName_NOT_Duplicated() {
        String nickName = "NotExistUser1";
        assertThat(userService.isDuplicatedNickName(nickName)).isEqualTo(UserServiceImpl.NOT_DUPLICATED);
    }

    UserEntity saveEntityByNickName(String nickName) {
        return userRepository.save(UserEntity.builder()
                .email("")
                .nickName(nickName)
                .password("")
                .profile("")
                .name("")
                .address("")
                .gender("")
                .birth("")
                .build());
    }

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
                .gender("M")
                .birth("19980830")
                .password("pwd")
                .address("SEOUL")
                .build());
    }
}