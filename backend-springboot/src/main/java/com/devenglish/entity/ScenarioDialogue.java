package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("scenario_dialogues")
public class ScenarioDialogue implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long scenarioId;
    
    private Long userId;
    
    private String role; // user, ai, system
    
    private String message;
    
    private String expectedResponse;
    
    private Integer sequenceNumber;
    
    private String evaluation;
    
    private Integer score;
    
    private String feedback;
    
    private String grammarErrors;
    
    private String vocabularySuggestions;
    
    private String pronunciationNotes;
    
    private Long responseTime;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}