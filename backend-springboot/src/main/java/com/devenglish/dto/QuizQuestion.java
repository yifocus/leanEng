package com.devenglish.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizQuestion {
    private Long id;
    private String question;
    private String options;
    private Integer correctAnswer;
    private String category;
    private String difficulty;
    private String explanation;
}