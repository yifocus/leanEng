package com.devenglish.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("quiz_questions")
public class QuizQuestion implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String question;
    
    private String options;
    
    private String correctAnswer;
    
    private String category;
    
    private String difficulty;
    
    private String explanation;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}