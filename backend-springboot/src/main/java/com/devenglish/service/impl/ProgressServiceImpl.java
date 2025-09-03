package com.devenglish.service.impl;

import com.devenglish.dto.DailyTaskResponse;
import com.devenglish.dto.ProgressResponse;
import com.devenglish.dto.StatisticsResponse;
import com.devenglish.service.ProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {
    
    @Override
    public Map<String, Object> getDashboardData(Long userId) {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("dailyProgress", 65);
        dashboard.put("totalTasks", 5);
        dashboard.put("completedTasks", 3);
        dashboard.put("streakDays", 7);
        return dashboard;
    }
    
    @Override
    public StatisticsResponse getStatistics(Long userId) {
        Map<String, Integer> weeklyProgress = new HashMap<>();
        weeklyProgress.put("Mon", 80);
        weeklyProgress.put("Tue", 65);
        weeklyProgress.put("Wed", 90);
        
        return StatisticsResponse.builder()
                .totalWords(500)
                .learnedWords(150)
                .totalScenarios(20)
                .completedScenarios(8)
                .totalQuizzes(100)
                .correctAnswers(75)
                .weeklyProgress(weeklyProgress)
                .build();
    }
    
    @Override
    public List<DailyTaskResponse> getDailyTasks(Long userId) {
        List<DailyTaskResponse> tasks = new ArrayList<>();
        tasks.add(DailyTaskResponse.builder()
                .id(1L)
                .title("Complete React Hooks documentation reading")
                .description("Read and understand React Hooks concepts")
                .category("Learning")
                .xpReward(50)
                .completed(false)
                .build());
        tasks.add(DailyTaskResponse.builder()
                .id(2L)
                .title("Practice Scrum meeting scenario")
                .description("Complete one Scrum meeting simulation")
                .category("Speaking")
                .xpReward(80)
                .completed(false)
                .build());
        return tasks;
    }
    
    @Override
    public Map<String, Object> completeTask(Long taskId, Long userId) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("xpEarned", 50);
        result.put("newLevel", false);
        return result;
    }
    
    @Override
    public List<Map<String, Object>> getLearningPath(Long userId) {
        List<Map<String, Object>> path = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> node = new HashMap<>();
            node.put("id", i);
            node.put("title", "Level " + i);
            node.put("completed", i <= 2);
            node.put("current", i == 3);
            path.add(node);
        }
        return path;
    }
    
    @Override
    public Integer updateStreak(Long userId) {
        return 8; // Return new streak days
    }
    
    @Override
    public Map<String, Object> addExperience(Long userId, Integer points) {
        Map<String, Object> result = new HashMap<>();
        result.put("newExperience", 2500);
        result.put("levelUp", false);
        result.put("newLevel", 12);
        return result;
    }
}