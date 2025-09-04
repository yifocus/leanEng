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
 * 成就实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("achievements")
public class Achievement {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("name")
    private String name;
    
    @TableField("description")
    private String description;
    
    @TableField("icon")
    private String icon;
    
    @TableField("xp_reward")
    private Integer xpReward;
    
    @TableField("requirement_type")
    private String requirementType;
    
    @TableField("requirement_value")
    private Integer requirementValue;
    
    @TableField("create_time")
    private LocalDateTime createTime;
}