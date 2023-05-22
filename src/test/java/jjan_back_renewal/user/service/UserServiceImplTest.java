package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.exception.NoSuchNicknameException;
import jjan_back_renewal.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
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
}