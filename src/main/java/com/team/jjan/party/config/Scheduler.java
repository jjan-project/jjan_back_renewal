package com.team.jjan.party.config;

import com.team.jjan.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    private final PartyService partyService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteParty(){
        partyService.deletePastParty();
    }

}
