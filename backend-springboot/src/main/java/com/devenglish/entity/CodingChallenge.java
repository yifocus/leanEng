package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 编程挑战实体类
 */
@Data
@TableName("coding_challenges")
public class CodingChallenge implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 挑战标题
     */
    private String title;
    
    /**
     * 挑战描述
     */
    private String description;
    
    /**
     * 难度级别
     */
    private String difficulty;
    
    /**
     * 编程语言
     */
    private String language;
    
    /**
     * 初始代码模板
     */
    private String template;
    
    /**
     * 测试用例
     */
    private String testCases;
    
    /**
     * 预期输出
     */
    private String expectedOutput;
    
    /**
     * 经验值奖励
     */
    private Integer xpReward;
    
    /**
     * 分类
     */
    private String category;
    
    /**
     * 时间限制（分钟）
     */
    private Integer timeLimit;
    
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