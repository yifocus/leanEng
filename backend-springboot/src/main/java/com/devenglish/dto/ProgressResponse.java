package com.devenglish.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgressResponse {
    private Integer dailyProgress;
    private Integer totalTasks;
    private Integer completedTasks;
    private Integer streakDays;
    private Integer totalLearningTime;
}