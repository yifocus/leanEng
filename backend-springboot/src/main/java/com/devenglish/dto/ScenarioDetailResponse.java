package com.devenglish.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ScenarioDetailResponse {
    private Long id;
    private String name;
    private String description;
    private String avatar;
    private String role;
    private String difficulty;
    private String category;
    private Integer estimatedTime;
    private String context;
    private String objectives;
    private List<String> keyPhrases;
    private List<String> tips;
    private List<DialogueTemplate> dialogueTemplates;
    private Map<String, List<String>> responseExamples;
    private ScenarioProgress userProgress;
    
    @Data
    @Builder
    public static class DialogueTemplate {
        private Integer sequence;
        private String speaker;
        private String message;
        private List<String> possibleResponses;
        private String hint;
        private List<String> keyPoints;
    }
    
    @Data
    @Builder
    public static class ScenarioProgress {
        private Integer completedTimes;
        private Integer bestScore;
        private Integer averageScore;
        private String lastAttempt;
        private Boolean isCompleted;
    }
}