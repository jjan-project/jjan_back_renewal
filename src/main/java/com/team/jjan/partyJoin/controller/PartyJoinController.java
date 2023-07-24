package com.team.jjan.partyJoin.controller;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.common.dto.LogIn;
import com.team.jjan.partyJoin.service.PartyJoinService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party/join")
public class PartyJoinController {

    private final PartyJoinService partyJoinService;

    @Operation(summary = "파티 가입", description = "가입 성공 시 SUCCESS code 반환, 파티 가입 시 로그인 정보 확인, Parameter 변수 id로 파티 정보 전달")
    @PostMapping("/{id}")
    public ResponseEntity<ResponseMessage> joinParty(@PathVariable("id") Long partyId,
                                                     @LogIn CurrentUser currentUser){
        return ResponseEntity.ok(partyJoinService.joinParty(partyId, currentUser));
    }
}
