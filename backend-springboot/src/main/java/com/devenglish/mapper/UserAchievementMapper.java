package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.UserAchievement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * 用户成就数据访问层
 */
@Mapper
public interface UserAchievementMapper extends BaseMapper<UserAchievement> {
    
    /**
     * 获取用户所有成就
     */
    @Select("SELECT ua.*, a.name, a.description, a.icon, a.xp_reward " +
            "FROM user_achievements ua " +
            "LEFT JOIN achievements a ON ua.achievement_id = a.id " +
            "WHERE ua.user_id = #{userId} " +
            "ORDER BY ua.unlocked_at DESC")
    List<UserAchievement> getUserAchievements(Long userId);
    
    /**
     * 获取用户已解锁的成就
     */
    @Select("SELECT ua.*, a.name, a.description, a.icon, a.xp_reward " +
            "FROM user_achievements ua " +
            "LEFT JOIN achievements a ON ua.achievement_id = a.id " +
            "WHERE ua.user_id = #{userId} AND ua.is_unlocked = true " +
            "ORDER BY ua.unlocked_at DESC")
    List<UserAchievement> getUnlockedAchievements(Long userId);
    
    /**
     * 获取用户未解锁的成就
     */
    @Select("SELECT ua.*, a.name, a.description, a.icon, a.xp_reward " +
            "FROM user_achievements ua " +
            "LEFT JOIN achievements a ON ua.achievement_id = a.id " +
            "WHERE ua.user_id = #{userId} AND ua.is_unlocked = false " +
            "ORDER BY ua.current_progress DESC")
    List<UserAchievement> getLockedAchievements(Long userId);
    
    /**
     * 获取用户特定成就
     */
    @Select("SELECT * FROM user_achievements WHERE user_id = #{userId} AND achievement_id = #{achievementId}")
    UserAchievement getUserAchievement(Long userId, Long achievementId);
    
    /**
     * 更新成就进度
     */
    @Update("UPDATE user_achievements SET current_progress = #{currentProgress}, " +
            "is_unlocked = CASE WHEN current_progress >= target_progress THEN true ELSE false END, " +
            "unlocked_at = CASE WHEN current_progress >= target_progress AND unlocked_at IS NULL THEN NOW() ELSE unlocked_at END " +
            "WHERE user_id = #{userId} AND achievement_id = #{achievementId}")
    int updateAchievementProgress(Long userId, Long achievementId, Integer currentProgress);
    
    /**
     * 解锁成就
     */
    @Update("UPDATE user_achievements SET is_unlocked = true, unlocked_at = NOW() " +
            "WHERE user_id = #{userId} AND achievement_id = #{achievementId}")
    int unlockAchievement(Long userId, Long achievementId);
    
    /**
     * 初始化用户成就（为新用户创建所有成就记录）
     */
    @Insert("INSERT INTO user_achievements (user_id, achievement_id, is_unlocked, current_progress, target_progress) " +
            "SELECT #{userId}, id, false, 0, requirement_value FROM achievements")
    int initializeUserAchievements(Long userId);
    
    /**
     * 获取用户成就统计
     */
    @Select("SELECT COUNT(*) as total, " +
            "SUM(CASE WHEN is_unlocked = true THEN 1 ELSE 0 END) as unlocked " +
            "FROM user_achievements WHERE user_id = #{userId}")
    UserAchievement getUserAchievementStats(Long userId);
}