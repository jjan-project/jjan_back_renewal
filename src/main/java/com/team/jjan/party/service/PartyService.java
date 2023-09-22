package com.team.jjan.party.service;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.party.dto.request.PartyCreateRequestDto;
import com.team.jjan.party.dto.request.PartySearchCondition;
import com.team.jjan.party.dto.request.PartyUpdateRequestDto;
import com.team.jjan.party.dto.response.PartyCreateResponseDto;
import com.team.jjan.party.dto.response.PartyGetAllResponseDto;
import com.team.jjan.party.dto.response.PartyGetResponseDto;
import com.team.jjan.party.dto.response.PartyUpdateResponseDto;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.exception.NoSuchPartyException;
import com.team.jjan.party.repository.PartyRepository;
import com.team.jjan.partyJoin.entity.PartyJoin;
import com.team.jjan.partyJoin.repository.PartyJoinRepository;
import com.team.jjan.upload.service.FileUploadService;
import com.team.jjan.user.entitiy.UserEntity;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.team.jjan.chat.entity.ChatRoom.createChatRoom;
import static com.team.jjan.common.ResponseCode.REQUEST_SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyJoinRepository partyJoinRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public ResponseMessage createParty(PartyCreateRequestDto partyCreateRequestDto, List<MultipartFile> images, CurrentUser currentUser){

        //파티 생성 유저
        UserEntity authorUser = getAuthorUser(currentUser);
        //파티 이미지 저장 & 변환
        List<String> imagesName = uploadImage(images);
        //파티 생성
        PartyEntity createParty = partyRepository.save(partyCreateRequestDto.toEntity(authorUser, imagesName));
        createParty.setChatRoom(createChatRoom(createParty));

        return ResponseMessage.of(REQUEST_SUCCESS, PartyCreateResponseDto.of(createParty));
    }

    public ResponseMessage getParty(Long partyId){
        PartyEntity getParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("존재하지 않는 파티입니다"));

        List<PartyJoin> getPartyJoinInfo = partyJoinRepository.findPartyJoinByJoinParty(getParty);

        return ResponseMessage.of(REQUEST_SUCCESS, new PartyGetResponseDto(getParty, getPartyJoinInfo));
    }

    public ResponseMessage getJoinParty(CurrentUser currentUser){

        List<PartyGetAllResponseDto> allJoinParty = new ArrayList<>();

        //현재 로그인 유저
        UserEntity user = getAuthorUser(currentUser);

        //현재 로그인 유저가 만든 파티
        partyRepository.findByAuthor(user).stream().map(PartyGetAllResponseDto::new).forEach(allJoinParty::add);

        //현재 로그인 유저가 가입한 파티
        partyRepository.findMyJoinParty(user).stream().map(PartyGetAllResponseDto::new).forEach(allJoinParty::add);

        return ResponseMessage.of(REQUEST_SUCCESS, allJoinParty);
    }

    public ResponseMessage getSearchParty(Pageable pageable, PartySearchCondition searchCondition, CurrentUser currentUser){

        //현재 로그인 유저
        UserEntity user = getAuthorUser(currentUser);

        List<PartyGetAllResponseDto> allPartyDto = partyRepository.findAllBySearch(pageable, searchCondition, user).stream().map(PartyGetAllResponseDto::new).collect(Collectors.toList());
        return ResponseMessage.of(REQUEST_SUCCESS, allPartyDto);
    }

    public ResponseMessage getAllParty(Pageable pageable){
        List<PartyGetAllResponseDto> allPartyDto = partyRepository.findAllParty(pageable).stream().map(PartyGetAllResponseDto::new).collect(Collectors.toList());
        return ResponseMessage.of(REQUEST_SUCCESS, allPartyDto);
    }

    public ResponseMessage updateParty(Long partyId, PartyUpdateRequestDto partyUpdateRequestDto, List<MultipartFile> images, CurrentUser currentUser){

        //접속 유저
        UserEntity authorUser = getAuthorUser(currentUser);
        //수정 대상 파티
        PartyEntity updateParty = getPartyFromId(partyId);
        //인가 확인
        if(!authorUser.equals(updateParty.getAuthor())) throw new BadCredentialsException("권한이 없습니다");
        //이미지 삭제
        updateParty.getPartyImages().forEach(fileUploadService::deleteFile);
        //파티 이미지 저장 & 변환
        List<String> imagesName = uploadImage(images);

        updateParty.update(partyUpdateRequestDto, imagesName);
        return ResponseMessage.of(REQUEST_SUCCESS, PartyUpdateResponseDto.of(updateParty, imagesName));
    }

    public ResponseMessage deleteParty(Long partyId, CurrentUser currentUser){

        //접속 유저
        UserEntity authorUser = getAuthorUser(currentUser);
        //삭제 대상 파티
        PartyEntity deleteParty = getPartyFromId(partyId);
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

    //파티 추출
    private PartyEntity getPartyFromId(Long partyId){
        return partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("존재하지 않는 파티입니다"));
    }

    //이미지 저장
    private List<String> uploadImage(List<MultipartFile> images){
        //이미지 미 업로드 시
        if(CollectionUtils.isEmpty(images)){
            return new ArrayList<>();
        }

        return images.stream().map(image -> {
            try {
                return fileUploadService.uploadFileToS3(image, UUID.randomUUID().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}

