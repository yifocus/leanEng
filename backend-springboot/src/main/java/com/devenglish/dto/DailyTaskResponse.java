package com.devenglish.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyTaskResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private Integer xpReward;
    private Boolean completed;
}