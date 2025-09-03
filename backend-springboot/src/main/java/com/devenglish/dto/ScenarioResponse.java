package com.devenglish.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScenarioResponse {
    private Long id;
    private String name;
    private String description;
    private String avatar;
    private String role;
    private String difficulty;
    private String category;
    private Integer estimatedTime;
}