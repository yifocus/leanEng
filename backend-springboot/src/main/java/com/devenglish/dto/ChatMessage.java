package com.devenglish.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private Long scenarioId;
    private Long userId;
    private String message;
    private String role;
    private Long timestamp;
}