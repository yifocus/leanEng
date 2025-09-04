package com.devenglish.dto;

import lombok.Data;

@Data
public class QuizSubmission {
    private Long userId;
    private Long questionId;
    private Long quizId;
    private String answer;
    private String userAnswer;
    private Boolean isCorrect;
    private Integer timeSpent;
}