package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.UserQuizHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用户答题历史Mapper接口
 */
@Mapper
public interface UserQuizHistoryMapper extends BaseMapper<UserQuizHistory> {
    
    /**
     * 查询用户在指定类型和难度下的正确率
     * @param userId 用户ID
     * @param quizType 题目类型
     * @param quizDifficulty 题目难度
     * @return 正确率（0-1之间的小数）
     */
    @Select("SELECT COALESCE(AVG(CASE WHEN is_correct = 1 THEN 1.0 ELSE 0.0 END), 0) " +
            "FROM user_quiz_history " +
            "WHERE user_id = #{userId} AND quiz_type = #{quizType} AND quiz_difficulty = #{quizDifficulty}")
    Double getAccuracyByTypeAndDifficulty(@Param("userId") Long userId, 
                                         @Param("quizType") String quizType, 
                                         @Param("quizDifficulty") String quizDifficulty);
    
    /**
     * 查询用户总体正确率
     * @param userId 用户ID
     * @return 正确率（0-1之间的小数）
     */
    @Select("SELECT COALESCE(AVG(CASE WHEN is_correct = 1 THEN 1.0 ELSE 0.0 END), 0) " +
            "FROM user_quiz_history WHERE user_id = #{userId}")
    Double getOverallAccuracy(@Param("userId") Long userId);
    
    /**
     * 查询用户最近的答题记录
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 答题历史列表
     */
    @Select("SELECT * FROM user_quiz_history " +
            "WHERE user_id = #{userId} " +
            "ORDER BY answer_time DESC LIMIT #{limit}")
    List<UserQuizHistory> getRecentHistory(@Param("userId") Long userId, @Param("limit") int limit);
    
    /**
     * 查询用户在指定时间段内的答题统计
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计信息
     */
    @Select("SELECT " +
            "COUNT(*) as totalQuestions, " +
            "SUM(CASE WHEN is_correct = 1 THEN 1 ELSE 0 END) as correctAnswers, " +
            "AVG(CASE WHEN is_correct = 1 THEN 1.0 ELSE 0.0 END) as accuracy, " +
            "SUM(xp_earned) as totalXp, " +
            "AVG(time_spent) as avgTimeSpent " +
            "FROM user_quiz_history " +
            "WHERE user_id = #{userId} AND answer_time BETWEEN #{startTime} AND #{endTime}")
    Map<String, Object> getStatisticsByTimeRange(@Param("userId") Long userId, 
                                                 @Param("startTime") LocalDateTime startTime, 
                                                 @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询用户各类型题目的统计信息
     * @param userId 用户ID
     * @return 各类型统计列表
     */
    @Select("SELECT " +
            "quiz_type, " +
            "COUNT(*) as totalQuestions, " +
            "SUM(CASE WHEN is_correct = 1 THEN 1 ELSE 0 END) as correctAnswers, " +
            "AVG(CASE WHEN is_correct = 1 THEN 1.0 ELSE 0.0 END) as accuracy " +
            "FROM user_quiz_history " +
            "WHERE user_id = #{userId} " +
            "GROUP BY quiz_type")
    List<Map<String, Object>> getStatisticsByType(@Param("userId") Long userId);
    
    /**
     * 查询用户各难度题目的统计信息
     * @param userId 用户ID
     * @return 各难度统计列表
     */
    @Select("SELECT " +
            "quiz_difficulty, " +
            "COUNT(*) as totalQuestions, " +
            "SUM(CASE WHEN is_correct = 1 THEN 1 ELSE 0 END) as correctAnswers, " +
            "AVG(CASE WHEN is_correct = 1 THEN 1.0 ELSE 0.0 END) as accuracy " +
            "FROM user_quiz_history " +
            "WHERE user_id = #{userId} " +
            "GROUP BY quiz_difficulty")
    List<Map<String, Object>> getStatisticsByDifficulty(@Param("userId") Long userId);
    
    /**
     * 查询用户偏好的题目类型（基于答题频率和正确率）
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 偏好类型列表
     */
    @Select("SELECT quiz_type, " +
            "COUNT(*) as frequency, " +
            "AVG(CASE WHEN is_correct = 1 THEN 1.0 ELSE 0.0 END) as accuracy, " +
            "(COUNT(*) * AVG(CASE WHEN is_correct = 1 THEN 1.0 ELSE 0.0 END)) as score " +
            "FROM user_quiz_history " +
            "WHERE user_id = #{userId} " +
            "GROUP BY quiz_type " +
            "ORDER BY score DESC " +
            "LIMIT #{limit}")
    List<Map<String, Object>> getPreferredTypes(@Param("userId") Long userId, @Param("limit") int limit);
    
    /**
     * 检查用户是否已经回答过某个题目
     * @param userId 用户ID
     * @param quizId 题目ID
     * @return 是否已回答
     */
    @Select("SELECT COUNT(*) > 0 FROM user_quiz_history WHERE user_id = #{userId} AND quiz_id = #{quizId}")
    Boolean hasAnswered(@Param("userId") Long userId, @Param("quizId") Long quizId);
    
    /**
     * 查询用户答错的题目ID列表
     * @param userId 用户ID
     * @param quizType 题目类型
     * @param limit 限制数量
     * @return 题目ID列表
     */
    @Select("SELECT DISTINCT quiz_id FROM user_quiz_history " +
            "WHERE user_id = #{userId} AND quiz_type = #{quizType} AND is_correct = 0 " +
            "ORDER BY answer_time DESC LIMIT #{limit}")
    List<Long> getIncorrectQuizIds(@Param("userId") Long userId, 
                                  @Param("quizType") String quizType, 
                                  @Param("limit") int limit);
}