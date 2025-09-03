package com.devenglish.service;

import com.devenglish.dto.QuizQuestion;
import com.devenglish.dto.QuizSubmission;
import java.util.List;
import java.util.Map;

public interface GameService {
    
    QuizQuestion getNextQuizQuestion(Long userId, String difficulty);
    
    Map<String, Object> submitQuizAnswer(QuizSubmission submission);
    
    Map<String, Object> getCodingChallenge(Long userId, String level);
    
    Map<String, Object> submitCodingChallenge(Long challengeId, Long userId, String code);
    
    List<Map<String, Object>> getAchievements(Long userId);
    
    Map<String, Object> unlockAchievement(Long achievementId, Long userId);
    
    List<Map<String, Object>> getLeaderboard(String period, Integer limit);
}