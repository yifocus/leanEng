package com.devenglish.service;

import com.devenglish.dto.DailyTaskResponse;
import com.devenglish.dto.ProgressResponse;
import com.devenglish.dto.StatisticsResponse;
import java.util.List;
import java.util.Map;

public interface ProgressService {
    
    Map<String, Object> getDashboardData(Long userId);
    
    StatisticsResponse getStatistics(Long userId);
    
    List<DailyTaskResponse> getDailyTasks(Long userId);
    
    Map<String, Object> completeTask(Long taskId, Long userId);
    
    List<Map<String, Object>> getLearningPath(Long userId);
    
    Integer updateStreak(Long userId);
    
    Map<String, Object> addExperience(Long userId, Integer points);
}