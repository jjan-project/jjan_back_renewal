package com.team.jjan.party.service;

import com.team.jjan.party.repository.PartyRepository;
import com.team.jjan.upload.FileUploadException;
import com.team.jjan.upload.FileUploadResponse;
import com.team.jjan.upload.FileUploadService;
import com.team.jjan.party.dto.PartyCreateRequestDto;
import com.team.jjan.party.dto.PartyDto;
import com.team.jjan.party.dto.PartyUpdateRequestDto;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.exception.NoSuchPartyException;
import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.repository.UserRepository;
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
            List<FileUploadResponse> fileUploadResponses = fileUploadService.uploadPartyImages(UUID.randomUUID().toString(), partyImages);
            for (FileUploadResponse dto : fileUploadResponses) {
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
    public PartyDto update(String userEmail, PartyUpdateRequestDto requestDto, List<MultipartFile> partyImages) {
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NoSuchEmailException(userEmail));
        if (!userEntity.getEmail().equals(requestDto.getUserEntity().getEmail())) {
            throw new BadCredentialsException("유저에게 해당 권한이 없습니다.");
        }
        PartyEntity party = partyRepository.findById(requestDto.getId())
                .orElseThrow(() -> new NoSuchPartyException("해당하는 파티 id 를 찾을 수 없습니다. : " + requestDto.getId()));
        List<String> partyImageUrls = new ArrayList<>();
        try {
            List<FileUploadResponse> fileUploadResponses = fileUploadService.uploadPartyImages(UUID.randomUUID().toString(), partyImages);
            for (FileUploadResponse dto : fileUploadResponses) {
                partyImageUrls.add(dto.getUrl());
            }
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
        party.update(requestDto, partyImageUrls);
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
