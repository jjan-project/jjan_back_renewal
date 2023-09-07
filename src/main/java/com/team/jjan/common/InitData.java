package com.team.jjan.common;

import com.team.jjan.join.dto.JoinRequest;
import com.team.jjan.party.entity.Location;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.party.repository.PartyRepository;
import com.team.jjan.user.dto.AddressRequest;
import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
        private final PasswordEncoder passwordEncoder;

        private final PartyTag[] partyTags = new PartyTag[]{PartyTag.TAG_00, PartyTag.TAG_02, PartyTag.TAG_04, PartyTag.TAG_06, PartyTag.TAG_01, PartyTag.TAG_03, PartyTag.TAG_05, PartyTag.TAG_07, PartyTag.TAG_09, PartyTag.TAG_14};

        public void init() {

            //유저 정보
            for (int i = 1; i <= 5; i++) {
                JoinRequest joinRequest = new JoinRequest(
                        "email" + i + "@naver.com",
                        "password",
                        "address",
                        new Date(2003-(i*2)-1900, Calendar.MARCH, 2),
                        37.487266 ,
                        126.854187 ,
                        "남성",
                        i + "번째 사용자",
                        "30");
                UserEntity user = UserEntity.createUserEntity(joinRequest, passwordEncoder.encode(joinRequest.getPassword()));
                userRepository.save(user);

                //파티 생성
                List<PartyTag> tags = new ArrayList<>();
                tags.add(partyTags[i-1]);
                tags.add(partyTags[i]);


                PartyEntity party = PartyEntity.builder()
                        .title(i + "의 게시글")
                        .content("서울에서 보자")
                        .maxPartyNum(4)
                        .location(new Location("서울시 어딘가", 37.512296-(5-i)*0.05, 127.102385-(5-i)*0.05))
                        .partyDate(new Date(2023 - 1900, Calendar.OCTOBER, 2+i, 19+i, 12+i))
                        .partyTags(tags)
                        .author(user)
                        .averageAge((long) LocalDate.now().getYear()-user.getBirth().getYear()+1-1900)
                        .build();

                partyRepository.save(party);
            }
        }

    }
}
