package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.QuizQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuizQuestionMapper extends BaseMapper<QuizQuestion> {
    
    /**
     * 根据难度随机获取一道题目
     * @param difficulty 难度等级
     * @return 随机题目
     */
    @Select("SELECT * FROM quiz_questions WHERE difficulty = #{difficulty} ORDER BY RAND() LIMIT 1")
    QuizQuestion getRandomQuestionByDifficulty(String difficulty);
    
    /**
     * 随机获取一道题目（不限难度）
     * @return 随机题目
     */
    @Select("SELECT * FROM quiz_questions ORDER BY RAND() LIMIT 1")
    QuizQuestion getRandomQuestion();
    
    /**
     * 根据难度获取题目列表
     * @param difficulty 难度等级
     * @return 题目列表
     */
    @Select("SELECT * FROM quiz_questions WHERE difficulty = #{difficulty}")
    List<QuizQuestion> getQuestionsByDifficulty(String difficulty);
}