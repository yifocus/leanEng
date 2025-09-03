package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("scenarios")
public class Scenario implements Serializable {
    
    @TableId(type = IdType.AUTO)
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
    
    private String keyPhrases;
    
    private String tips;
    
    private Integer totalDialogues;
    
    private Integer minScore;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}