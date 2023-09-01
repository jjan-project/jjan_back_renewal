package com.team.jjan.common;

import com.team.jjan.join.dto.JoinRequest;
import com.team.jjan.party.entity.Location;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.party.repository.PartyRepository;
import com.team.jjan.partyJoin.entity.PartyJoin;
import com.team.jjan.partyJoin.repository.PartyJoinRepository;
import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData {

    private final InitDataService initDataService;

    @PostConstruct
    public void init() {
        initDataService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitDataService {

        private final UserRepository userRepository;
        private final PartyRepository partyRepository;
        private final PartyJoinRepository partyJoinRepository;
        private final PasswordEncoder passwordEncoder;

        public void init() {

            //유저 정보
            for (int i = 1; i <= 5; i++) {
                JoinRequest joinRequest = new JoinRequest(
                        "email" + i + "@naver.com",
                        "password",
                        "address",
                        new Date(2000 - 1900, Calendar.MARCH, 2),
                        "남성",
                        i + "번째 사용자",
                        "30");
                UserEntity user = UserEntity.createUserEntity(joinRequest, passwordEncoder.encode(joinRequest.getPassword()));
                userRepository.save(user);

                //파티 생성
                List<PartyTag> tags = new ArrayList<>();
                tags.add(PartyTag.TAG_01);
                tags.add(PartyTag.TAG_03);

                PartyEntity party = PartyEntity.builder()
                        .title(i + "의 게시글")
                        .content("서울에서 보자")
                        .maxPartyNum(4)
                        .location(new Location(126.882685, 37.501096))
                        .partyDate(new Date(2023 - 1900, Calendar.OCTOBER, 2, 19, 12))
                        .partyTags(tags)
                        .author(user)
                        .build();

                partyRepository.save(party);
            }

            //파티 가입
            for (int i = 2; i <=5 ; i++) {
                UserEntity user = userRepository.findById((long) i).orElseThrow();
                PartyEntity party = partyRepository.findById((long) (i-1)).orElseThrow();

                partyJoinRepository.save(PartyJoin.builder()
                        .joinUser(user)
                        .joinParty(party)
                        .build());
            }

        }

    }
}
