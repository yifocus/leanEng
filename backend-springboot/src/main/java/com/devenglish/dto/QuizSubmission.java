package com.devenglish.dto;

import lombok.Data;

@Data
public class QuizSubmission {
    private Long userId;
    private Long questionId;
    private String answer;
    private Boolean isCorrect;
}