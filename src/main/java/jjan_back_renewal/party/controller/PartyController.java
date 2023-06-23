package jjan_back_renewal.party.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jjan_back_renewal.join.auth.JwtProvider;
import jjan_back_renewal.party.dto.PartyCreateRequestDto;
import jjan_back_renewal.party.dto.PartyCreateResponseDto;
import jjan_back_renewal.party.dto.PartyDto;
import jjan_back_renewal.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party")
public class PartyController {

    private final PartyService partyService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "글쓰기", description = "글씁니다")
    @PostMapping
    public ResponseEntity<PartyCreateResponseDto> writePost(@RequestBody PartyCreateRequestDto createRequestDto, HttpServletRequest request) {
        String userEmail = jwtProvider.getUserEmail(request);
        PartyDto write = partyService.write(userEmail, createRequestDto);
        return ResponseEntity.ok().body(new PartyCreateResponseDto(write));

    }
}