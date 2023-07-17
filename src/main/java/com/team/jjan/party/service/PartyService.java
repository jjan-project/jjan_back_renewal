package com.team.jjan.party.service;

import com.team.jjan.party.dto.PartyCreateRequestDto;
import com.team.jjan.party.dto.PartyDto;
import com.team.jjan.party.dto.PartyUpdateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PartyService {
    PartyDto write(String userEmail, PartyCreateRequestDto requestDto, List<MultipartFile> partyImages);

    PartyDto read(Long partyId);

    PartyDto update(String userEmail, PartyUpdateRequestDto requestDto, List<MultipartFile> partyImages);

    PartyDto delete(Long partyId);
}
