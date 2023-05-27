package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.LoginRequestDto;
import jjan_back_renewal.user.dto.LoginResponseDto;
import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.exception.NoSuchNicknameException;
import jjan_back_renewal.user.repository.UserRepository;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.util.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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
        userRepository.deleteById(exist.getId());
    }

    @Test
    @DisplayName("DB에 있는 NickName에 isDuplicatedNickName을 호출하면 NOT_DUPLICATED 값을 반환하지 않는다. ")
    void isDuplicatedNickName_Duplicated() {
        String nickName = "ExistUser1";
        UserEntity newUser = saveEntityByNickName(nickName);
        assertThat(userService.isDuplicatedNickName(nickName)).isNotEqualTo(UserServiceImpl.NOT_DUPLICATED);
        userRepository.deleteById(newUser.getId());
    }


    @Test
    @DisplayName("DB에 없는 NickName에 isDuplicatedNickName을 호출하면 NOT_DUPLICATED 값을 반환한다. ")
    void isDuplicatedNickName_NOT_Duplicated() {
        String nickName = "NotExistUser1";
        assertThat(userService.isDuplicatedNickName(nickName)).isEqualTo(UserServiceImpl.NOT_DUPLICATED);
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
    @DisplayName("DB 에 있는 이메일에 isDuplicatedEmail 호출하면 NOT_DUPLICATED 값을 반환하지 않는다.")
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

    @Test
    @DisplayName("DB 안에 저장된 이메일 & 패스워드 조합으로 로그인 시 성공한다.")
    void login_있는조합_success() {
        String email = "test@naver.com";
        String password = "1111";
        UserEntity userEntity = saveEntityForLogin(email, password);
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        LoginResponseDto login = userService.login(loginRequestDto);
        assertThat(login.getUserDto().getEmail()).isEqualTo(email);
        assertThat(passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword())).isTrue();
        userRepository.deleteById(userEntity.getId());
    }

    @Test
    @DisplayName("DB 안에 없는 이메일로 로그인 시 실패한다.")
    void login_없는이메일_fail() {
        String email = "test@naver.com";
        String password = "1111";
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        assertThatThrownBy(
                () -> userService.login(loginRequestDto))
                .isExactlyInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("잘못된 이메일 & 비밀번호 조합으로 로그인 시 실패한다.")
    void login_잘못된조합_fail() {
        String email = "test@naver.com";
        String password = "1111";
        UserEntity userEntity = saveEntityForLogin(email, password);
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password + "!!");
        assertThatThrownBy(
                () -> userService.login(loginRequestDto))
                .isExactlyInstanceOf(BadCredentialsException.class);
        userRepository.deleteById(userEntity.getId());
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

    UserEntity saveEntityByEmail(String email) {
        return userRepository.save(UserEntity.builder()
                .email(email)
                .nickName("nickname")
                .name("testname")
                .gender("M")
                .birth("19980830")
                .password("pwd")
                .address("SEOUL")
                .profile("")
                .build());
    }

    @Test
    @DisplayName("회원가입 api 테스팅")
    void join() {
        UserEntity user = userRepository.save(UserEntity.builder()
                .email("phoenix1228@daum.net")
                .nickName("Limworld98")
                .password("testpassword")
                .profile("")
                .name("")
                .address("SEOUL")
                .gender("M")
                .birth("19980108")
                .nickName("nickName")
                .password("")
                .profile("")
                .name("")
                .address("")
                .gender("")
                .birth("")
                .build());

        assertThat(userService.findByNickName("Limworld98").getId()).isEqualTo(user.getId());
        assertThat(userService.findByEmail("phoenix1228@daum.net").getId()).isEqualTo(user.getId());
    }

    UserEntity saveEntityForLogin(String email, String password) {
        return userRepository.save(UserEntity.builder()
                .email(email)
                .nickName("nickName")
                .password(passwordEncoder.encode(password))
                .profile("")
                .name("")
                .address("")
                .gender("")
                .birth("")
                .roles(List.of(Role.ROLE_MEMBER))
                .build());
    }

}