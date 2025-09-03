package com.devenglish.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class StatisticsResponse {
    private Integer totalWords;
    private Integer learnedWords;
    private Integer totalScenarios;
    private Integer completedScenarios;
    private Integer totalQuizzes;
    private Integer correctAnswers;
    private Map<String, Integer> weeklyProgress;
}