package com.team.jjan.partyJoin.controller;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.common.dto.LogIn;
import com.team.jjan.partyJoin.dto.PartyExitRequestDto;
import com.team.jjan.partyJoin.service.PartyJoinService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party")
public class PartyJoinController {

    private final PartyJoinService partyJoinService;

    @Operation(summary = "파티 가입", description = "가입 성공 시 SUCCESS code 반환, 파티 가입 시 로그인 정보 확인, Parameter 변수 id로 파티 정보 전달")
    @PostMapping("/{id}/join")
    public ResponseEntity<ResponseMessage> joinParty(@PathVariable("id") Long partyId,
                                                     @LogIn CurrentUser currentUser){
        return ResponseEntity.ok().body(partyJoinService.joinParty(partyId, currentUser));
    }

    @Operation(summary = "파티 탈퇴", description = "탈퇴 성공 시 SUCCESS code 반환, 파티 탈퇴 시 로그인 정보 확인, Parameter 변수 id로 파티 정보 전달")
    @PostMapping("/{id}/exit")
    public ResponseEntity<ResponseMessage> exitParty(@PathVariable("id") Long partyId,
                                                     @RequestBody PartyExitRequestDto partyExitRequestDto,
                                                     @LogIn CurrentUser currentUser){
        return ResponseEntity.ok().body(partyJoinService.exitParty(partyId, partyExitRequestDto, currentUser));
    }
}
