package com.team.jjan.partyJoin.service;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.party.entity.PartyEntity;
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

import static com.team.jjan.common.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PartyJoinService {

    private final UserRepository userRepository;
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

        partyJoinRepository.save(PartyJoin.createJoin(joinUser, party));
        return ResponseMessage.of(REQUEST_SUCCESS);
    }

}
