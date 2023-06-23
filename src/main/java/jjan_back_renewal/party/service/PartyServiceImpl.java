package jjan_back_renewal.party.service;

import jjan_back_renewal.party.dto.PartyCreateRequestDto;
import jjan_back_renewal.party.dto.PartyDto;
import jjan_back_renewal.party.entity.PartyEntity;
import jjan_back_renewal.party.exception.NoSuchPartyException;
import jjan_back_renewal.party.repository.PartyRepository;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public PartyDto write(String userEmail, PartyCreateRequestDto requestDto) {
        UserEntity userEntity = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchEmailException(userEmail));
        PartyDto partyDto = new PartyDto(userEntity, requestDto);
        PartyEntity save = partyRepository.save(partyDto.toEntity());
        return new PartyDto(save);
    }

    @Transactional(readOnly = true)
    @Override
    public PartyDto read(Long partyId) {
        PartyEntity partyEntity = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("해당하는 파티 id 를 찾을 수 없습니다. : " + partyId));
        return new PartyDto(partyEntity);
    }

    @Override
    public PartyDto update() {
        return null;
    }

    @Override
    public PartyDto delete(Long partyId) {
        return null;
    }
}
