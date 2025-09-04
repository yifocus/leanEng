package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.Quiz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 测验题目Mapper接口
 */
@Mapper
public interface QuizMapper extends BaseMapper<Quiz> {
    
    /**
     * 根据类型和难度查询题目
     * @param type 题目类型
     * @param difficulty 难度级别
     * @param limit 限制数量
     * @return 题目列表
     */
    @Select("SELECT * FROM quiz_questions WHERE type = #{type} AND difficulty = #{difficulty} ORDER BY RAND() LIMIT #{limit}")
    List<Quiz> findByTypeAndDifficulty(@Param("type") String type, @Param("difficulty") String difficulty, @Param("limit") int limit);
    
    /**
     * 根据难度查询题目
     * @param difficulty 难度级别
     * @param limit 限制数量
     * @return 题目列表
     */
    @Select("SELECT * FROM quiz_questions WHERE difficulty = #{difficulty} ORDER BY RAND() LIMIT #{limit}")
    List<Quiz> findByDifficulty(@Param("difficulty") String difficulty, @Param("limit") int limit);
    
    /**
     * 根据类型查询题目
     * @param type 题目类型
     * @param limit 限制数量
     * @return 题目列表
     */
    @Select("SELECT * FROM quiz_questions WHERE type = #{type} ORDER BY RAND() LIMIT #{limit}")
    List<Quiz> findByType(@Param("type") String type, @Param("limit") int limit);
    
    /**
     * 查询用户未答过的题目
     * @param userId 用户ID
     * @param type 题目类型
     * @param difficulty 难度级别
     * @param limit 限制数量
     * @return 题目列表
     */
    @Select("SELECT q.* FROM quiz_questions q " +
            "LEFT JOIN user_quiz_history h ON q.id = h.quiz_id AND h.user_id = #{userId} " +
            "WHERE q.type = #{type} AND q.difficulty = #{difficulty} " +
            "AND h.quiz_id IS NULL " +
            "ORDER BY RAND() LIMIT #{limit}")
    List<Quiz> findUnansweredByUserAndTypeAndDifficulty(@Param("userId") Long userId, 
                                                        @Param("type") String type, 
                                                        @Param("difficulty") String difficulty, 
                                                        @Param("limit") int limit);
    
    /**
     * 查询用户答错的题目（用于复习）
     * @param userId 用户ID
     * @param type 题目类型
     * @param difficulty 难度级别
     * @param limit 限制数量
     * @return 题目列表
     */
    @Select("SELECT q.* FROM quiz_questions q " +
            "INNER JOIN user_quiz_history h ON q.id = h.quiz_id " +
            "WHERE h.user_id = #{userId} AND q.type = #{type} AND q.difficulty = #{difficulty} " +
            "AND h.is_correct = 0 " +
            "ORDER BY h.answer_time DESC, RAND() LIMIT #{limit}")
    List<Quiz> findIncorrectByUserAndTypeAndDifficulty(@Param("userId") Long userId, 
                                                       @Param("type") String type, 
                                                       @Param("difficulty") String difficulty, 
                                                       @Param("limit") int limit);
    
    /**
     * 智能推荐下一题（基于用户历史和偏好）
     * @param userId 用户ID
     * @param preferredTypes 偏好的题目类型
     * @param currentDifficulty 当前难度
     * @param limit 限制数量
     * @return 题目列表
     */
    @Select("<script>" +
            "SELECT q.* FROM quiz_questions q " +
            "LEFT JOIN user_quiz_history h ON q.id = h.quiz_id AND h.user_id = #{userId} " +
            "WHERE 1=1 " +
            "<if test='preferredTypes != null and preferredTypes.size() > 0'>" +
            "AND q.type IN " +
            "<foreach item='type' collection='preferredTypes' open='(' separator=',' close=')'>" +
            "#{type}" +
            "</foreach>" +
            "</if>" +
            "AND q.difficulty = #{currentDifficulty} " +
            "AND (h.quiz_id IS NULL OR h.is_correct = 0) " +
            "ORDER BY " +
            "CASE WHEN h.quiz_id IS NULL THEN 1 ELSE 2 END, " +
            "RAND() " +
            "LIMIT #{limit}" +
            "</script>")
    List<Quiz> findRecommendedQuizzes(@Param("userId") Long userId, 
                                     @Param("preferredTypes") List<String> preferredTypes, 
                                     @Param("currentDifficulty") String currentDifficulty, 
                                     @Param("limit") int limit);
    
    /**
     * 随机获取题目
     * @param limit 限制数量
     * @return 题目列表
     */
    @Select("SELECT * FROM quiz_questions ORDER BY RAND() LIMIT #{limit}")
    List<Quiz> findRandomQuizzes(@Param("limit") int limit);
}