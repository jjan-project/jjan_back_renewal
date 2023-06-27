package jjan_back_renewal.join.service;

import jjan_back_renewal.user.exception.NoSuchNicknameException;
import jjan_back_renewal.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RandomNicknameGenerateServiceTest {
    @Autowired
    RandomNicknameGenerateService randomNicknameGenerateService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("랜덤 생성 닉네임 유효성 테스트")
    void randomNicknameGenerateTest() {
        String nickname = randomNicknameGenerateService.generateRandomNickname(8);
        assertThat(nickname).isNotNull();
        assertThat(nickname.length()).isLessThan(9);
        assertThat(userRepository.findByNickName(nickname).isEmpty()).isTrue();
    }

}