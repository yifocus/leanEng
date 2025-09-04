package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户学习进度实体类
 */
@Data
@TableName("user_progress")
public class UserProgress implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 总经验值
     */
    private Integer totalXp;
    
    /**
     * 当前等级
     */
    private Integer currentLevel;
    
    /**
     * 连续学习天数
     */
    private Integer streakDays;
    
    /**
     * 最长连续学习天数
     */
    private Integer maxStreakDays;
    
    /**
     * 完成的任务数量
     */
    private Integer completedTasks;
    
    /**
     * 完成的场景数量
     */
    private Integer completedScenarios;
    
    /**
     * 完成的编程挑战数量
     */
    private Integer completedChallenges;
    
    /**
     * 解锁的成就数量
     */
    private Integer unlockedAchievements;
    
    /**
     * 总学习时间（分钟）
     */
    private Integer totalStudyTime;
    
    /**
     * 最后学习时间
     */
    private LocalDateTime lastStudyTime;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}