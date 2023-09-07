package com.team.jjan.partyJoin.service;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.party.entity.Message;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.service.PartyService;
import com.team.jjan.partyJoin.dto.PartyExitRequestDto;
import com.team.jjan.partyJoin.entity.PartyJoin;
import com.team.jjan.party.exception.NoSuchPartyException;
import com.team.jjan.partyJoin.exception.AlreadyJoinException;
import com.team.jjan.partyJoin.repository.PartyJoinRepository;
import com.team.jjan.party.repository.PartyRepository;
import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.team.jjan.common.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PartyJoinService {

    private final UserRepository userRepository;
    private final PartyService partyService;
    private final PartyRepository partyRepository;
    private final PartyJoinRepository partyJoinRepository;

    public ResponseMessage joinParty(Long partyId, CurrentUser currentUser){
        //가입 유저
        UserEntity joinUser = userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new NoSuchEmailException(currentUser.getEmail()));
        //가입 대상 파티
        PartyEntity party = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("존재하지 않는 파티입니다"));

        //이미 가입되어 있는지 확인
        if(joinUser.equals(party.getAuthor()) || partyJoinRepository.existsPartyJoinByJoinUserAndJoinParty(joinUser, party)){
            throw new AlreadyJoinException("이미 가입되어 있습니다");
        }

        party.updateAverageAge((long) LocalDate.now().getYear()-joinUser.getBirth().getYear()+1-1900, true);
        partyJoinRepository.save(PartyJoin.createJoin(joinUser, party));
        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    public ResponseMessage exitParty(Long partyId, CurrentUser currentUser){
        //탈퇴 유저
        UserEntity exitUser = userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new NoSuchEmailException(currentUser.getEmail()));
        //탈퇴 대상 파티
        PartyEntity party = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("존재하지 않는 파티입니다"));

        //작성자라면
        if(exitUser.equals(party.getAuthor())){
            return partyService.deleteParty(partyId, currentUser);
        }

        //가입되어 있는지 확인
        if(!partyJoinRepository.existsPartyJoinByJoinUserAndJoinParty(exitUser, party)){
            throw new AlreadyJoinException("가입하지 않은 파티입니다.");
        }

        party.updateAverageAge((long) LocalDate.now().getYear()-exitUser.getBirth().getYear()+1-1900, false);
        partyJoinRepository.deletePartyJoinByJoinUserAndJoinParty(exitUser, party);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

}
