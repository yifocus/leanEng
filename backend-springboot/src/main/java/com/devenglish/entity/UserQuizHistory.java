package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户答题历史实体类
 * 映射到数据库中的user_quiz_history表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_quiz_history")
public class UserQuizHistory {
    
    /**
     * 历史记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 题目ID
     */
    @TableField("quiz_id")
    private Long quizId;
    
    /**
     * 用户选择的答案 (A, B, C, D)
     */
    @TableField("user_answer")
    private String userAnswer;
    
    /**
     * 是否回答正确
     */
    @TableField("is_correct")
    private Boolean isCorrect;
    
    /**
     * 答题用时（秒）
     */
    @TableField("time_spent")
    private Integer timeSpent;
    
    /**
     * 获得的经验值
     */
    @TableField("xp_earned")
    private Integer xpEarned;
    
    /**
     * 答题时间
     */
    @TableField("answer_time")
    private LocalDateTime answerTime;
    
    /**
     * 题目类型 (冗余字段，便于统计)
     */
    @TableField("quiz_type")
    private String quizType;
    
    /**
     * 题目难度 (冗余字段，便于统计)
     */
    @TableField("quiz_difficulty")
    private String quizDifficulty;
    
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