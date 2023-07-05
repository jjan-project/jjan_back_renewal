package jjan_back_renewal.party.service;

import jjan_back_renewal.party.dto.PartyCreateRequestDto;
import jjan_back_renewal.party.dto.PartyDto;
import jjan_back_renewal.party.dto.PartyUpdateRequestDto;
import jjan_back_renewal.party.entity.PartyEntity;
import jjan_back_renewal.party.exception.NoSuchPartyException;
import jjan_back_renewal.party.repository.PartyRepository;
import jjan_back_renewal.upload.FileUploadException;
import jjan_back_renewal.upload.FileUploadResponseDto;
import jjan_back_renewal.upload.FileUploadService;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    @Override
    public PartyDto write(String userEmail, PartyCreateRequestDto requestDto, List<MultipartFile> partyImages) {
        UserEntity author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NoSuchEmailException(userEmail));
        List<String> partyImageUrls = new ArrayList<>();
        try {
            List<FileUploadResponseDto> fileUploadResponseDtos = fileUploadService.uploadPartyImages(UUID.randomUUID().toString(), partyImages);
            for (FileUploadResponseDto dto : fileUploadResponseDtos) {
                partyImageUrls.add(dto.getUrl());
            }
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
        PartyDto partyDto = new PartyDto(author, requestDto, partyImageUrls);
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

    @Transactional
    @Override
    public PartyDto update(String userEmail, PartyUpdateRequestDto requestDto) {
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NoSuchEmailException(userEmail));
        if (!userEntity.getEmail().equals(requestDto.getUserEntity().getEmail())) {
            throw new BadCredentialsException("유저에게 해당 권한이 없습니다.");
        }
        PartyEntity party = partyRepository.findById(requestDto.getId())
                .orElseThrow(() -> new NoSuchPartyException("해당하는 파티 id 를 찾을 수 없습니다. : " + requestDto.getId()));
        party.update(requestDto);
        return new PartyDto(party);
    }

    @Transactional
    @Override
    public PartyDto delete(Long partyId) {
        PartyEntity partyEntity = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("해당하는 파티 id 를 찾을 수 없습니다. : " + partyId));
        partyRepository.deleteById(partyId);
        return new PartyDto(partyEntity);
    }
}
