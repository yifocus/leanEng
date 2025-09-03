package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("vocabulary")
public class Vocabulary implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String term;
    
    private String definition;
    
    private String category;
    
    private String pronunciation;
    
    private String exampleSentence;
    
    private String translation;
    
    private String difficulty;
    
    private String tags;
    
    private Integer learnCount;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}