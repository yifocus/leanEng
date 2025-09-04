package com.devenglish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devenglish.entity.DailyTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 每日任务数据访问层
 */
@Mapper
public interface DailyTaskMapper extends BaseMapper<DailyTask> {
    
    /**
     * 根据用户ID获取每日任务列表
     */
    @Select("SELECT * FROM daily_tasks WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<DailyTask> getDailyTasksByUserId(Long userId);
    
    /**
     * 获取用户未完成的任务
     */
    @Select("SELECT * FROM daily_tasks WHERE user_id = #{userId} AND is_completed = false ORDER BY create_time DESC")
    List<DailyTask> getUncompletedTasksByUserId(Long userId);
    
    /**
     * 获取用户已完成的任务
     */
    @Select("SELECT * FROM daily_tasks WHERE user_id = #{userId} AND is_completed = true ORDER BY create_time DESC")
    List<DailyTask> getCompletedTasksByUserId(Long userId);
    
    /**
     * 完成任务
     */
    @Update("UPDATE daily_tasks SET is_completed = true WHERE id = #{taskId} AND user_id = #{userId}")
    int completeTask(Long taskId, Long userId);
    
    /**
     * 根据分类获取任务
     */
    @Select("SELECT * FROM daily_tasks WHERE category = #{category} ORDER BY create_time DESC")
    List<DailyTask> getTasksByCategory(String category);
}