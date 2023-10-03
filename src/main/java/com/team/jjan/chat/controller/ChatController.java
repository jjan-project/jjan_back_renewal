package com.team.jjan.chat.controller;

import com.team.jjan.chat.dto.ChatResponse;
import com.team.jjan.chat.dto.ChatRoomResponse;
import com.team.jjan.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{partyId}")
    public ResponseEntity findChatDataById(@PathVariable long partyId) {
        List<ChatResponse> result = chatService.findChatDataByPartyId(partyId);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/participants")
    public ResponseEntity findChattingRoomByUserId(@CookieValue String accessToken) {
        List<ChatRoomResponse> result = chatService.findChattingRoomByUserId(accessToken);

        return ResponseEntity.ok().body(result);
    }

}
