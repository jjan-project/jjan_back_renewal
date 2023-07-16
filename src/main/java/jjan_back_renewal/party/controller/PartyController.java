package jjan_back_renewal.party.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jjan_back_renewal.jwt.support.JwtProvider;
import jjan_back_renewal.party.dto.PartyCreateRequestDto;
import jjan_back_renewal.party.dto.PartyResponseDto;
import jjan_back_renewal.party.dto.PartyDto;
import jjan_back_renewal.party.dto.PartyUpdateRequestDto;
import jjan_back_renewal.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party")
public class PartyController {

    private final PartyService partyService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "글쓰기", description = "PartyCreateRequestDto 이외에 List<MultipartFile> 로 partyImage 를 보내줘야 합니다.")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PartyResponseDto> writeParty(@RequestPart PartyCreateRequestDto createRequestDto,
                                                       @RequestPart List<MultipartFile> partyImages,
                                                       HttpServletRequest request) {
        String userEmail = jwtProvider.getUserEmail(request);
        PartyDto write = partyService.write(userEmail, createRequestDto, partyImages);
        return ResponseEntity.ok().body(new PartyResponseDto(write));
    }

    @Operation(summary = "글읽기", description = "해당하는 id 의 Party 정보를 가져옵니다. 형식 : {\"partyId\" : [[id]]}")
    @GetMapping
    public ResponseEntity<PartyResponseDto> readParty(@RequestBody Map<String, Long> map) {
        return ResponseEntity.ok().body(new PartyResponseDto(partyService.read(map.get("partyId"))));
    }

    @Operation(summary = "글수정", description = "글을 수정합니다.")
    @PutMapping
    public ResponseEntity<PartyResponseDto> updateParty(@RequestPart PartyUpdateRequestDto updateRequestDto,
                                                        @RequestPart List<MultipartFile> partyImages,
                                                        HttpServletRequest request) {
        String userEmail = jwtProvider.getUserEmail(request);
        PartyDto write = partyService.update(userEmail, updateRequestDto, partyImages);
        return ResponseEntity.ok().body(new PartyResponseDto(write));
    }

    @Operation(summary = "글삭제", description = "해당하는 id 의 Party 를 삭제합니다. 형식 : {\"partyId\" : [[id]]}")
    @DeleteMapping
    public ResponseEntity<PartyResponseDto> deleteParty(@RequestBody Map<String, Long> map) {
        return ResponseEntity.ok().body(new PartyResponseDto(partyService.delete(map.get("partyId"))));
    }

}