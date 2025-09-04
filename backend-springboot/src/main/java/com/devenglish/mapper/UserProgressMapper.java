package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.UserProgress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * 用户学习进度数据访问层
 */
@Mapper
public interface UserProgressMapper extends BaseMapper<UserProgress> {
    
    /**
     * 根据用户ID获取学习进度
     */
    @Select("SELECT * FROM user_progress WHERE user_id = #{userId}")
    UserProgress getUserProgress(Long userId);
    
    /**
     * 更新用户经验值
     */
    @Update("UPDATE user_progress SET total_xp = total_xp + #{xp}, " +
            "current_level = FLOOR(SQRT(total_xp / 100)) + 1 " +
            "WHERE user_id = #{userId}")
    int addUserXp(Long userId, Integer xp);
    
    /**
     * 更新连续学习天数
     */
    @Update("UPDATE user_progress SET " +
            "streak_days = CASE " +
            "  WHEN DATE(last_study_time) = DATE(NOW() - INTERVAL 1 DAY) THEN streak_days + 1 " +
            "  WHEN DATE(last_study_time) < DATE(NOW() - INTERVAL 1 DAY) THEN 1 " +
            "  ELSE streak_days " +
            "END, " +
            "max_streak_days = GREATEST(max_streak_days, streak_days), " +
            "last_study_time = NOW() " +
            "WHERE user_id = #{userId}")
    int updateStreakDays(Long userId);
    
    /**
     * 增加完成任务数量
     */
    @Update("UPDATE user_progress SET completed_tasks = completed_tasks + 1 WHERE user_id = #{userId}")
    int incrementCompletedTasks(Long userId);
    
    /**
     * 增加完成场景数量
     */
    @Update("UPDATE user_progress SET completed_scenarios = completed_scenarios + 1 WHERE user_id = #{userId}")
    int incrementCompletedScenarios(Long userId);
    
    /**
     * 增加完成编程挑战数量
     */
    @Update("UPDATE user_progress SET completed_challenges = completed_challenges + 1 WHERE user_id = #{userId}")
    int incrementCompletedChallenges(Long userId);
    
    /**
     * 增加解锁成就数量
     */
    @Update("UPDATE user_progress SET unlocked_achievements = unlocked_achievements + 1 WHERE user_id = #{userId}")
    int incrementUnlockedAchievements(Long userId);
    
    /**
     * 增加学习时间
     */
    @Update("UPDATE user_progress SET total_study_time = total_study_time + #{minutes} WHERE user_id = #{userId}")
    int addStudyTime(Long userId, Integer minutes);
    
    /**
     * 初始化用户进度（为新用户创建进度记录）
     */
    @Insert("INSERT INTO user_progress (user_id, total_xp, current_level, streak_days, max_streak_days, " +
            "completed_tasks, completed_scenarios, completed_challenges, unlocked_achievements, " +
            "total_study_time, last_study_time) " +
            "VALUES (#{userId}, 0, 1, 0, 0, 0, 0, 0, 0, 0, NOW())")
    int initializeUserProgress(Long userId);
    
    /**
     * 获取排行榜数据（按经验值排序）
     */
    @Select("SELECT up.*, u.username, u.avatar " +
            "FROM user_progress up " +
            "LEFT JOIN users u ON up.user_id = u.id " +
            "ORDER BY up.total_xp DESC " +
            "LIMIT #{limit}")
    List<UserProgress> getLeaderboard(Integer limit);
    
    /**
     * 获取用户排名
     */
    @Select("SELECT COUNT(*) + 1 FROM user_progress WHERE total_xp > (SELECT total_xp FROM user_progress WHERE user_id = #{userId})")
    Integer getUserRank(Long userId);
    
    /**
     * 获取活跃用户统计
     */
    @Select("SELECT COUNT(*) FROM user_progress WHERE DATE(last_study_time) = DATE(NOW())")
    Integer getTodayActiveUsers();
}