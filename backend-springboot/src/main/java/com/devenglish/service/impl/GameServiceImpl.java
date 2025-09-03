package com.devenglish.service.impl;

import com.devenglish.dto.QuizQuestion;
import com.devenglish.dto.QuizSubmission;
import com.devenglish.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    
    @Override
    public QuizQuestion getNextQuizQuestion(Long userId, String difficulty) {
        return QuizQuestion.builder()
                .id(1L)
                .question("What does 'refactor' mean in software development?")
                .options("[\"Add new features\", \"Restructure existing code without changing functionality\", \"Delete old code\", \"Write documentation\"]")
                .correctAnswer("Restructure existing code without changing functionality")
                .category("Development")
                .difficulty("BEGINNER")
                .explanation("Refactoring improves code structure and readability without altering its external behavior.")
                .build();
    }
    
    @Override
    public Map<String, Object> submitQuizAnswer(QuizSubmission submission) {
        Map<String, Object> result = new HashMap<>();
        result.put("correct", submission.getIsCorrect());
        result.put("xpEarned", submission.getIsCorrect() ? 10 : 0);
        result.put("explanation", "Good job!");
        return result;
    }
    
    @Override
    public Map<String, Object> getCodingChallenge(Long userId, String level) {
        Map<String, Object> challenge = new HashMap<>();
        challenge.put("id", 1L);
        challenge.put("title", "Array Filter Implementation");
        challenge.put("description", "Implement a function that filters an array based on a condition");
        challenge.put("difficulty", level != null ? level : "INTERMEDIATE");
        return challenge;
    }
    
    @Override
    public Map<String, Object> submitCodingChallenge(Long challengeId, Long userId, String code) {
        Map<String, Object> result = new HashMap<>();
        result.put("passed", true);
        result.put("testsPassed", 5);
        result.put("totalTests", 5);
        result.put("xpEarned", 100);
        return result;
    }
    
    @Override
    public List<Map<String, Object>> getAchievements(Long userId) {
        List<Map<String, Object>> achievements = new ArrayList<>();
        
        Map<String, Object> achievement1 = new HashMap<>();
        achievement1.put("id", 1L);
        achievement1.put("name", "First Steps");
        achievement1.put("description", "Complete your first lesson");
        achievement1.put("icon", "🎯");
        achievement1.put("unlocked", true);
        achievements.add(achievement1);
        
        Map<String, Object> achievement2 = new HashMap<>();
        achievement2.put("id", 2L);
        achievement2.put("name", "Week Warrior");
        achievement2.put("description", "Maintain a 7-day learning streak");
        achievement2.put("icon", "🔥");
        achievement2.put("unlocked", true);
        achievements.add(achievement2);
        
        Map<String, Object> achievement3 = new HashMap<>();
        achievement3.put("id", 3L);
        achievement3.put("name", "Vocabulary Master");
        achievement3.put("description", "Learn 100 technical terms");
        achievement3.put("icon", "📚");
        achievement3.put("unlocked", false);
        achievements.add(achievement3);
        
        return achievements;
    }
    
    @Override
    public Map<String, Object> unlockAchievement(Long achievementId, Long userId) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("xpEarned", 200);
        result.put("achievementName", "New Achievement Unlocked!");
        return result;
    }
    
    @Override
    public List<Map<String, Object>> getLeaderboard(String period, Integer limit) {
        List<Map<String, Object>> leaderboard = new ArrayList<>();
        
        String[] names = {"Alex Chen", "Maria Garcia", "David Kim", "Sarah Johnson", "Tom Wilson"};
        int[] scores = {12450, 11200, 10800, 9450, 8900};
        
        for (int i = 0; i < Math.min(limit, names.length); i++) {
            Map<String, Object> user = new HashMap<>();
            user.put("rank", i + 1);
            user.put("username", names[i]);
            user.put("nickname", names[i]);
            user.put("experience", scores[i]);
            user.put("level", 10 + i);
            leaderboard.add(user);
        }
        
        return leaderboard;
    }
}