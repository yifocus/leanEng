package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 测验题目实体类
 * 映射到数据库中的quiz表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("quiz_questions")
public class Quiz {
    
    /**
     * 题目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 题目内容
     */
    @TableField("question")
    private String question;
    
    /**
     * 题目类型
     */
    @TableField("type")
    private String type;
    
    /**
     * 选项列表 (JSON格式存储)
     */
    @TableField("options")
    private String options;
    
    /**
     * 正确答案
     */
    @TableField("correct_answer")
    private String correctAnswer;
    
    /**
     * 题目分类
     */
    @TableField("category")
    private String category;
    
    /**
     * 难度级别
     */
    @TableField("difficulty")
    private String difficulty;
    
    /**
     * 题目解释
     */
    @TableField("explanation")
    private String explanation;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}