package jjan_back_renewal.user.service;

import jjan_back_renewal.join.dto.LoginRequestDto;
import jjan_back_renewal.join.dto.LoginResponseDto;
import jjan_back_renewal.join.service.JoinService;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.repository.UserRepository;
import jjan_back_renewal.user.entitiy.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class JoinServiceTest {

    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JoinService joinService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("DB 안에 저장된 이메일 & 패스워드 조합으로 로그인 시 성공한다.")
    void login_있는조합_success() {
        String email = "test@naver.com";
        String password = "1111";
        UserEntity userEntity = saveEntityForLogin(email, password);
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        LoginResponseDto login = joinService.login(loginRequestDto);
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
                () -> joinService.login(loginRequestDto))
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
                () -> joinService.login(loginRequestDto))
                .isExactlyInstanceOf(BadCredentialsException.class);
        userRepository.deleteById(userEntity.getId());
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
