package com.team.jjan.party.service;

import com.team.jjan.common.dto.SessionUser;
import com.team.jjan.party.dto.PartyCreateRequestDto;
import com.team.jjan.party.dto.PartyDto;
import com.team.jjan.party.dto.PartyUpdateRequestDto;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.upload.service.FileUploadService;
import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.repository.UserRepository;
import com.team.jjan.party.exception.NoSuchPartyException;
import com.team.jjan.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public PartyDto createParty(PartyCreateRequestDto partyCreateRequestDto, List<MultipartFile> images, SessionUser sessionUser){

        //파티 생성 유저
        UserEntity createUser = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new NoSuchEmailException(sessionUser.getEmail()));
        //파티 이미지 저장 & 변환
        List<String> imagesName = uploadImage(images);
        //파티 생성
        PartyEntity createParty = partyRepository.save(partyCreateRequestDto.toEntity(createUser, imagesName));
        return PartyDto.of(createParty);
    }

    @Transactional(readOnly = true)
    public PartyDto getParty(Long partyId){
        PartyEntity getParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("존재하지 않는 파티입니다"));

        return PartyDto.of(getParty);
    }

    public PartyDto updateParty(Long partyId, PartyUpdateRequestDto partyUpdateRequestDto,
                                List<MultipartFile> images, SessionUser sessionUser){

        //접속 유저
        UserEntity createUser = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new NoSuchEmailException(sessionUser.getEmail()));
        //수정 대상 파티
        PartyEntity updateParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("존재하지 않는 파티입니다"));
        //인가 확인
        if(!createUser.equals(updateParty.getAuthor())) throw new BadCredentialsException("권한이 없습니다");
        //파티 이미지 저장 & 변환
        List<String> imagesName = uploadImage(images);

        updateParty.update(partyUpdateRequestDto, imagesName);
        return PartyDto.of(updateParty);
    }

    public void deleteParty(Long PartyId){
        partyRepository.deleteById(PartyId);
    }

    //이미지 저장
    public List<String> uploadImage(List<MultipartFile> images){
        return images.stream().map(image -> {
            try {
                return fileUploadService.uploadFileToS3(image, UUID.randomUUID().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}

