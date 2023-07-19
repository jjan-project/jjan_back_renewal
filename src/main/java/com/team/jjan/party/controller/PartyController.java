package com.team.jjan.party.controller;

import com.team.jjan.common.dto.LogIn;
import com.team.jjan.common.dto.SessionUser;
import com.team.jjan.party.dto.PartyCreateRequestDto;
import com.team.jjan.party.dto.PartyDto;
import com.team.jjan.party.dto.PartyResponseDto;
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
    public PartyResponseDto createParty(@RequestPart PartyCreateRequestDto createRequestDto,
                                        @RequestPart  List<MultipartFile> images,
                                        @LogIn SessionUser sessionUser) {
        PartyDto createParty = partyService.createParty(createRequestDto, images, sessionUser);
        return new PartyResponseDto(createParty);
    }

    @Operation(summary = "파티 조회", description = "파티 정보를 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PartyResponseDto getParty(@PathVariable("id") Long partyId){
        PartyDto getParty = partyService.getParty(partyId);
        return new PartyResponseDto(getParty);
    }

    @Operation(summary = "파티 수정", description = "PartyUpdateRequestDto와 List<MultipartFile> 분리, 글 수정 시 로그인 정보 확인")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public PartyResponseDto updateParty(@PathVariable("id") Long partyId,
                                        @RequestPart PartyUpdateRequestDto updateRequestDto,
                                        @RequestPart List<MultipartFile> images,
                                        @LogIn SessionUser sessionUser){
        PartyDto updateParty = partyService.updateParty(partyId, updateRequestDto, images, sessionUser);
        return new PartyResponseDto(updateParty);
    }

    @Operation(summary = "파티 삭제", description = "삭제 성공 시 'ok' 반환")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public String deleteParty(@PathVariable("id") Long partyId){
        partyService.deleteParty(partyId);
        return "ok";
    }
}
