package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 每日任务实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("daily_tasks")
public class DailyTask {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("title")
    private String title;
    
    @TableField("description")
    private String description;
    
    @TableField("category")
    private String category;
    
    @TableField("xp_reward")
    private Integer xpReward;
    
    @TableField("is_completed")
    private Boolean isCompleted;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("create_time")
    private LocalDateTime createTime;
}