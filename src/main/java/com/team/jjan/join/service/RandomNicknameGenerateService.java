package com.team.jjan.join.service;

import com.google.gson.Gson;
import com.team.jjan.common.ResponseCode;
import com.team.jjan.common.ResponseMessage;
import com.team.jjan.join.exception.ApiServerException;
import com.team.jjan.join.dto.RandomNicknameApiResponseDto;
import com.team.jjan.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.team.jjan.common.ResponseCode.REQUEST_SUCCESS;

@Service
@Slf4j
@RequiredArgsConstructor
public class RandomNicknameGenerateService {

    private final UserRepository userRepository;
    private final Gson gson;

    public ResponseMessage generateRandomNickname() {
        int maxLength = 8;

        String nickname = "";
        do {
            String response = randomNicknameAPI(1, maxLength);
            nickname = gson.fromJson(response, RandomNicknameApiResponseDto.class).getWords().get(0);
        } while (userRepository.findByNickName(nickname).isPresent());

        return ResponseMessage.of(REQUEST_SUCCESS , REQUEST_SUCCESS.getMessage() , nickname);
    }

    private String randomNicknameAPI(int count, int maxLength) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://nickname.hwanmoo.kr/?format=json&count=" + count + "&max_length=" + maxLength)
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new ApiServerException("랜덤 닉네임 생성 API 서버에 문제가 발생했습니다.");
        }
        return responseEntity.getBody();
    }

}
