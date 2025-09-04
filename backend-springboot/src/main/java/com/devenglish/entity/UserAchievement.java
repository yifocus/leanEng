package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户成就实体类
 */
@Data
@TableName("user_achievements")
public class UserAchievement implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 成就ID
     */
    private Long achievementId;
    
    /**
     * 是否已解锁
     */
    private Boolean isUnlocked;
    
    /**
     * 当前进度
     */
    private Integer currentProgress;
    
    /**
     * 目标进度
     */
    private Integer targetProgress;
    
    /**
     * 解锁时间
     */
    private LocalDateTime unlockedAt;
    
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