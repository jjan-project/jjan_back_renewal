package jjan_back_renewal.party.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jjan_back_renewal.join.auth.JwtProvider;
import jjan_back_renewal.party.dto.PartyCreateRequestDto;
import jjan_back_renewal.party.dto.PartyResponseDto;
import jjan_back_renewal.party.dto.PartyDto;
import jjan_back_renewal.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party")
public class PartyController {

    private final PartyService partyService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "글쓰기", description = "글씁니다")
    @PostMapping
    public ResponseEntity<PartyResponseDto> writeParty(@RequestBody PartyCreateRequestDto createRequestDto, HttpServletRequest request) {
        String userEmail = jwtProvider.getUserEmail(request);
        PartyDto write = partyService.write(userEmail, createRequestDto);
        return ResponseEntity.ok().body(new PartyResponseDto(write));
    }

    @Operation(summary = "글읽기", description = "해당하는 id 의 Party 정보를 가져옵니다.")
    @GetMapping
    public ResponseEntity<PartyResponseDto> readParty(@RequestBody Long partyId) {
        return ResponseEntity.ok().body(new PartyResponseDto(partyService.read(partyId)));
    }

    @Operation(summary = "글삭제", description = "해당하는 id 의 Party 를 삭제합니다.")
    @GetMapping
    public ResponseEntity<PartyResponseDto> deleteParty(@RequestBody Long partyId) {
        return ResponseEntity.ok().body(new PartyResponseDto(partyService.delete(partyId)));
    }


}