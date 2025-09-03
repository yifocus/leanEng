package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String username;
    
    @JsonIgnore
    private String password;
    
    private String email;
    
    private String nickname;
    
    private String avatarUrl;
    
    private String englishLevel;
    
    private String learningGoal;
    
    private Integer level;
    
    private Integer experience;
    
    private Integer streakDays;
    
    private Integer totalLearningTime;
    
    private Integer dailyTarget;
    
    private Boolean enabled;
    
    private LocalDateTime lastLoginTime;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
}