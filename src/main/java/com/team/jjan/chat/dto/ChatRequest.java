package com.team.jjan.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {

    private String sender;

    private String senderImage;

    private String message;

    private long partyId;

    private MessageType messageType;

}