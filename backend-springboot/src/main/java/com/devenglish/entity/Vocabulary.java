package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("vocabulary")
public class Vocabulary implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String term;
    
    private String definition;
    
    private String definitionCn;
    
    private String category;
    
    private String pronunciation;
    
    private String exampleSentence;
    
    private String exampleSentenceCn;
    
    private String translation;
    
    private String difficulty;
    
    private String tags;
    
    private Integer learnCount;
    
    // 父子关系字段
    private Long parentId;
    
    private Boolean isParent;
    
    // 子词汇列表（不存储在数据库中）
    @TableField(exist = false)
    private List<Vocabulary> children;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}