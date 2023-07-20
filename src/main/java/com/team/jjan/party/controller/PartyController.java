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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party")
public class PartyController {

    private final PartyService partyService;

    @Operation(summary = "파티 생성", description = "PartyCreateRequestDto와 List<MultipartFile> 분리, 글 작성 시 로그인 정보 확인")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseMessage createParty(@RequestPart PartyCreateRequestDto createRequestDto,
                                       @RequestPart  List<MultipartFile> images,
                                       @LogIn CurrentUser sessionUser) {
        return partyService.createParty(createRequestDto, images, sessionUser);
    }

    @Operation(summary = "파티 조회", description = "파티 정보를 조회, Parameter 변수 id로 정보 전달")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseMessage getParty(@PathVariable("id") Long partyId){
        return partyService.getParty(partyId);
    }

    @Operation(summary = "파티 수정", description = "PartyUpdateRequestDto와 List<MultipartFile> 분리, 글 수정 시 로그인 정보 확인,  Parameter 변수 id로 정보 전달")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ResponseMessage updateParty(@PathVariable("id") Long partyId,
                                        @RequestPart PartyUpdateRequestDto updateRequestDto,
                                        @RequestPart List<MultipartFile> images,
                                        @LogIn CurrentUser sessionUser){
        return partyService.updateParty(partyId, updateRequestDto, images, sessionUser);
    }

    @Operation(summary = "파티 삭제", description = "삭제 성공 시 SUCCESS code 반환")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseMessage deleteParty(@PathVariable("id") Long partyId){
        return partyService.deleteParty(partyId);
    }
}
