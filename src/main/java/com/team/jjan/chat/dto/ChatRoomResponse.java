package com.team.jjan.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatRoomResponse {
    private long chatId;

    private long meetingId;

    private String meetingTitle;

    private List<String> meetingImages;

}