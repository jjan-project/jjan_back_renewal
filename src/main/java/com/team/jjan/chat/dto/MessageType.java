package com.team.jjan.chat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MessageType {
    ENTER , SEND;

    @JsonCreator
    public static MessageType from(String s) {
        return MessageType.valueOf(s.toUpperCase());
    }
}
