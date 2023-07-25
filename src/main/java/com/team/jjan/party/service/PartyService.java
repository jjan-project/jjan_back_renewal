package com.team.jjan.party.service;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.party.dto.*;
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

import static com.team.jjan.common.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public ResponseMessage createParty(PartyCreateRequestDto partyCreateRequestDto, List<MultipartFile> images, CurrentUser currentUser){

        //파티 생성 유저
        UserEntity authorUser = getAuthorUser(currentUser);
        //파티 이미지 저장 & 변환
        List<String> imagesName = uploadImage(images);
        //파티 생성
        PartyEntity createParty = partyRepository.save(partyCreateRequestDto.toEntity(authorUser, imagesName));
        return ResponseMessage.of(REQUEST_SUCCESS, PartyCreateResponseDto.of(createParty));
    }

    public ResponseMessage getParty(Long partyId){
        PartyEntity getParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("존재하지 않는 파티입니다"));

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    public ResponseMessage updateParty(Long partyId, PartyUpdateRequestDto partyUpdateRequestDto, List<MultipartFile> images, CurrentUser currentUser){

        //접속 유저
        UserEntity authorUser = getAuthorUser(currentUser);
        //수정 대상 파티
        PartyEntity updateParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("존재하지 않는 파티입니다"));
        //인가 확인
        if(!authorUser.equals(updateParty.getAuthor())) throw new BadCredentialsException("권한이 없습니다");
        //파티 이미지 저장 & 변환
        List<String> imagesName = uploadImage(images);

        updateParty.update(partyUpdateRequestDto, imagesName);
        return ResponseMessage.of(REQUEST_SUCCESS, PartyUpdateResponseDto.of(updateParty, imagesName));
    }

    public ResponseMessage deleteParty(Long partyId, CurrentUser currentUser){

        //접속 유저
        UserEntity authorUser = getAuthorUser(currentUser);
        //삭제 대상 파티
        PartyEntity deleteParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("존재하지 않는 파티입니다"));
        //인가 확인
        if(!authorUser.equals(deleteParty.getAuthor())) throw new BadCredentialsException("권한이 없습니다");
        //이미지 삭제
        deleteParty.getPartyImages().forEach(fileUploadService::deleteFile);
        //파티 삭제
        partyRepository.deleteById(partyId);

        return ResponseMessage.of(REQUEST_SUCCESS);
    }

    //유저 추출
    private UserEntity getAuthorUser(CurrentUser currentUser){
        return userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new NoSuchEmailException(currentUser.getEmail()));
    }

    //이미지 저장
    private List<String> uploadImage(List<MultipartFile> images){
        return images.stream().map(image -> {
            try {
                return fileUploadService.uploadFileToS3(image, UUID.randomUUID().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}

