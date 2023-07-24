package com.team.jjan.party.controller;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.LogIn;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.party.dto.PartyCreateRequestDto;
import com.team.jjan.party.dto.PartyUpdateRequestDto;
import com.team.jjan.party.service.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party")
public class PartyController {

    private final PartyService partyService;

    @Operation(summary = "파티 생성", description = "PartyCreateRequestDto와 List<MultipartFile> 분리, 글 작성 시 로그인 정보 확인")
    @PostMapping
    public ResponseEntity<ResponseMessage> createParty(@RequestPart(value = "data") PartyCreateRequestDto createRequestDto,
                                                       @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                       @LogIn CurrentUser currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partyService.createParty(createRequestDto, images, currentUser));
    }

    @Operation(summary = "파티 조회", description = "파티 정보를 조회, Parameter 변수 id로 정보 전달")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getParty(@PathVariable("id") Long partyId){
        return ResponseEntity.ok().body(partyService.getParty(partyId));
    }

    @Operation(summary = "파티 수정", description = "PartyUpdateRequestDto와 List<MultipartFile> 분리, 글 수정 시 로그인 정보 확인,  Parameter 변수 id로 정보 전달")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateParty(@PathVariable("id") Long partyId,
                                        @RequestPart(value = "data") PartyUpdateRequestDto updateRequestDto,
                                        @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                        @LogIn CurrentUser currentUser){
        return ResponseEntity.ok().body(partyService.updateParty(partyId, updateRequestDto, images, currentUser));
    }

    @Operation(summary = "파티 삭제", description = "삭제 성공 시 SUCCESS code 반환")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteParty(@PathVariable("id") Long partyId,
                                                       @LogIn CurrentUser currentUser){
        return ResponseEntity.ok(partyService.deleteParty(partyId, currentUser));
    }
}
