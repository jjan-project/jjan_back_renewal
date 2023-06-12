package jjan_back_renewal.join.service;

import com.google.gson.Gson;
import jjan_back_renewal.join.dto.RandomNicknameApiResponseDto;
import jjan_back_renewal.join.exception.ApiServerException;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class RandomNicknameGenerateService {

    private final UserRepository userRepository;
    private final Gson gson;

    public String generateRandomNickname(int maxLength) {
        if (maxLength < 6) {
            log.warn("maxLength 는 6 이상이어야 하므로 자동으로 length = 6인 닉네임 생성");
        }
        String nickname = "";
        do {
            String response = randomNicknameAPI(1, maxLength);
            nickname = gson.fromJson(response, RandomNicknameApiResponseDto.class).getWords().get(0);
        } while (userRepository.findByNickName(nickname).isPresent());
        return nickname;
    }

    private static String randomNicknameAPI(int count, int maxLength) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://nickname.hwanmoo.kr/?format=json&count=" + count + "&max_length=" + maxLength)
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (!statusCode.is2xxSuccessful()) {
            throw new ApiServerException("랜덤 닉네임 생성 API 서버에 문제가 있음");
        }
        return responseEntity.getBody();
    }

}
