package jjan_back_renewal.party.service;

import jjan_back_renewal.party.dto.PartyCreateRequestDto;
import jjan_back_renewal.party.dto.PartyDto;
import jjan_back_renewal.party.dto.PartyUpdateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PartyService {
    PartyDto write(String userEmail, PartyCreateRequestDto requestDto, List<MultipartFile> partyImages);

    PartyDto read(Long partyId);

    PartyDto update(String userEmail, PartyUpdateRequestDto requestDto, List<MultipartFile> partyImages);

    PartyDto delete(Long partyId);
}
