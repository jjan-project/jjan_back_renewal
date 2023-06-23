package jjan_back_renewal.party.service;

import jjan_back_renewal.party.dto.PartyCreateRequestDto;
import jjan_back_renewal.party.dto.PartyDto;

public interface PartyService {
    PartyDto write(String userEmail, PartyCreateRequestDto requestDto);
    PartyDto read(Long partyId);
    PartyDto update();
    PartyDto delete(Long partyId);
}
