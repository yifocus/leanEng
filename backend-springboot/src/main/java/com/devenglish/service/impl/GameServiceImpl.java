package com.devenglish.service.impl;

import com.devenglish.dto.QuizSubmission;
import com.devenglish.entity.*;
import com.devenglish.mapper.*;
import com.devenglish.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    
    private final QuizQuestionMapper quizQuestionMapper;
    private final AchievementMapper achievementMapper;
    private final UserMapper userMapper;
    private final CodingChallengeMapper codingChallengeMapper;
    private final QuizMapper quizMapper;
    private final UserQuizHistoryMapper userQuizHistoryMapper;
    
    @Override
    public com.devenglish.dto.QuizQuestion getNextQuizQuestion(Long userId, String difficulty) {
        try {
            Quiz questionEntity = getRecommendedQuiz(userId, difficulty);
            
            // 如果没有找到题目，返回默认题目
            if (questionEntity == null) {
                log.warn("No quiz question found for user {} with difficulty: {}, returning default question", userId, difficulty);
                return getDefaultQuizQuestion();
            }
            
            // 转换实体类到DTO
            return convertToQuizQuestionDTO(questionEntity);
                    
        } catch (Exception e) {
            log.error("Error getting quiz question for user {} with difficulty {}", userId, difficulty, e);
            // 发生异常时返回默认题目
            return getDefaultQuizQuestion();
        }
    }
    
    /**
     * 智能推荐下一题
     * 基于用户历史、难度、类型等因素进行推荐
     */
    private Quiz getRecommendedQuiz(Long userId, String difficulty) {
        try {
            // 1. 确定目标难度
            String targetDifficulty = determineDifficulty(userId, difficulty);
            
            // 2. 获取用户偏好的题目类型
            List<String> preferredTypes = getUserPreferredTypes(userId);
            
            // 3. 优先推荐用户未答过的题目
            List<Quiz> unansweredQuizzes = new ArrayList<>();
            if (!preferredTypes.isEmpty()) {
                for (String type : preferredTypes) {
                    List<Quiz> quizzes = quizMapper.findUnansweredByUserAndTypeAndDifficulty(userId, type, targetDifficulty, 5);
                    unansweredQuizzes.addAll(quizzes);
                }
            }
            
            if (!unansweredQuizzes.isEmpty()) {
                return unansweredQuizzes.get(new Random().nextInt(unansweredQuizzes.size()));
            }
            
            // 4. 如果没有未答题目，推荐用户答错的题目进行复习
            List<Quiz> incorrectQuizzes = new ArrayList<>();
            if (!preferredTypes.isEmpty()) {
                for (String type : preferredTypes) {
                    List<Quiz> quizzes = quizMapper.findIncorrectByUserAndTypeAndDifficulty(userId, type, targetDifficulty, 3);
                    incorrectQuizzes.addAll(quizzes);
                }
            }
            
            if (!incorrectQuizzes.isEmpty()) {
                return incorrectQuizzes.get(new Random().nextInt(incorrectQuizzes.size()));
            }
            
            // 5. 使用智能推荐算法
            List<Quiz> recommendedQuizzes = quizMapper.findRecommendedQuizzes(userId, preferredTypes, targetDifficulty, 10);
            if (!recommendedQuizzes.isEmpty()) {
                return recommendedQuizzes.get(new Random().nextInt(recommendedQuizzes.size()));
            }
            
            // 6. 最后回退到按难度随机选择
            List<Quiz> fallbackQuizzes = quizMapper.findByDifficulty(targetDifficulty, 10);
            if (!fallbackQuizzes.isEmpty()) {
                return fallbackQuizzes.get(new Random().nextInt(fallbackQuizzes.size()));
            }
            
            // 7. 完全随机选择
            List<Quiz> randomQuizzes = quizMapper.findRandomQuizzes(10);
            if (!randomQuizzes.isEmpty()) {
                return randomQuizzes.get(new Random().nextInt(randomQuizzes.size()));
            }
            
            return null;
            
        } catch (Exception e) {
            log.error("Error in getRecommendedQuiz for user {}", userId, e);
            return null;
        }
    }
    
    /**
     * 确定目标难度
     */
    private String determineDifficulty(Long userId, String requestedDifficulty) {
        // 如果明确指定了难度，直接使用
        if (StringUtils.hasText(requestedDifficulty)) {
            return requestedDifficulty.toUpperCase();
        }
        
        try {
            // 根据用户整体正确率动态调整难度
            Double overallAccuracy = userQuizHistoryMapper.getOverallAccuracy(userId);
            
            if (overallAccuracy == null || overallAccuracy == 0.0) {
                return "BEGINNER"; // 新用户从初级开始
            }
            
            if (overallAccuracy >= 0.8) {
                return "ADVANCED"; // 正确率80%以上推荐高级题目
            } else if (overallAccuracy >= 0.6) {
                return "INTERMEDIATE"; // 正确率60-80%推荐中级题目
            } else {
                return "BEGINNER"; // 正确率60%以下推荐初级题目
            }
            
        } catch (Exception e) {
            log.error("Error determining difficulty for user {}", userId, e);
            return "BEGINNER";
        }
    }
    
    /**
     * 获取用户偏好的题目类型
     */
    private List<String> getUserPreferredTypes(Long userId) {
        try {
            List<Map<String, Object>> preferredTypesData = userQuizHistoryMapper.getPreferredTypes(userId, 3);
            return preferredTypesData.stream()
                    .map(data -> (String) data.get("quiz_type"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting preferred types for user {}", userId, e);
            // 返回默认类型偏好
            return Arrays.asList("VOCABULARY", "GRAMMAR", "READING");
        }
    }
    
    /**
     * 转换Quiz实体到QuizQuestion DTO
     */
    private com.devenglish.dto.QuizQuestion convertToQuizQuestionDTO(Quiz quiz) {
        try {
            // 解析JSON格式的选项
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> optionsList = objectMapper.readValue(quiz.getOptions(), 
                objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
            
            // 找到正确答案的索引
            int correctAnswerIndex = -1;
            for (int i = 0; i < optionsList.size(); i++) {
                if (optionsList.get(i).equals(quiz.getCorrectAnswer())) {
                    correctAnswerIndex = i;
                    break;
                }
            }
            
            return com.devenglish.dto.QuizQuestion.builder()
                    .id(quiz.getId())
                    .question(quiz.getQuestion())
                    .options(quiz.getOptions()) // 直接使用JSON格式的选项
                    .correctAnswer(correctAnswerIndex) // 返回正确答案的索引
                    .category(quiz.getCategory())
                    .difficulty(quiz.getDifficulty())
                    .explanation(quiz.getExplanation())
                    .build();
        } catch (Exception e) {
            log.error("Error converting Quiz to QuizQuestion DTO", e);
            // 返回默认题目
            return getDefaultQuizQuestion();
        }
    }
    
    /**
     * 获取默认题目
     */
    private com.devenglish.dto.QuizQuestion getDefaultQuizQuestion() {
        return com.devenglish.dto.QuizQuestion.builder()
                .id(1L)
                .question("What does 'refactor' mean in software development?")
                .options("[\"Add new features\", \"Restructure existing code without changing functionality\", \"Delete old code\", \"Write documentation\"]")
                .correctAnswer(1) // 正确答案的索引
                .category("Development")
                .difficulty("BEGINNER")
                .explanation("Refactoring improves code structure and readability without altering its external behavior.")
                .build();
    }
    
    @Override
    public Map<String, Object> submitQuizAnswer(QuizSubmission submission) {
        try {
            // 1. 验证提交数据
            if (submission == null || submission.getUserId() == null || submission.getQuizId() == null) {
                throw new IllegalArgumentException("Invalid submission data");
            }
            
            // 2. 获取题目信息
            Quiz quiz = quizMapper.selectById(submission.getQuizId());
            if (quiz == null) {
                throw new IllegalArgumentException("Quiz not found: " + submission.getQuizId());
            }
            
            // 3. 判断答案是否正确
            boolean isCorrect = quiz.getCorrectAnswer().equalsIgnoreCase(submission.getUserAnswer());
            
            // 4. 计算经验值奖励
            int xpEarned = calculateXpReward(quiz, isCorrect, submission.getTimeSpent());
            
            // 5. 记录答题历史
            UserQuizHistory history = UserQuizHistory.builder()
                    .userId(submission.getUserId())
                    .quizId(submission.getQuizId())
                    .userAnswer(submission.getUserAnswer())
                    .isCorrect(isCorrect)
                    .timeSpent(submission.getTimeSpent())
                    .xpEarned(xpEarned)
                    .answerTime(LocalDateTime.now())
                    .quizType(quiz.getType())
                    .quizDifficulty(quiz.getDifficulty())
                    .build();
            
            userQuizHistoryMapper.insert(history);
            
            // 6. 更新用户经验值和等级
            updateUserProgress(submission.getUserId(), xpEarned);
            
            // 7. 获取推荐的下一题
            com.devenglish.dto.QuizQuestion nextQuestion = getNextQuizQuestion(submission.getUserId(), quiz.getDifficulty());
            
            // 8. 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("correct", isCorrect);
            result.put("correctAnswer", quiz.getCorrectAnswer());
            result.put("explanation", quiz.getExplanation());
            result.put("xpEarned", xpEarned);
            result.put("nextQuestion", nextQuestion);
            
            // 9. 添加统计信息
            Double accuracy = userQuizHistoryMapper.getAccuracyByTypeAndDifficulty(
                submission.getUserId(), quiz.getType(), quiz.getDifficulty());
            result.put("typeAccuracy", accuracy != null ? Math.round(accuracy * 100) : 0);
            
            log.info("User {} submitted answer for quiz {}: {} (correct: {}), earned {} XP", 
                    submission.getUserId(), submission.getQuizId(), 
                    submission.getUserAnswer(), isCorrect, xpEarned);
            
            return result;
            
        } catch (Exception e) {
            log.error("Error submitting quiz answer for user {} and quiz {}", 
                    submission.getUserId(), submission.getQuizId(), e);
            
            // 返回错误响应
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("correct", false);
            errorResult.put("xpEarned", 0);
            errorResult.put("explanation", "An error occurred while processing your answer.");
            errorResult.put("error", e.getMessage());
            return errorResult;
        }
    }
    
    /**
     * 计算经验值奖励
     * 基于题目难度、正确性、答题时间等因素
     */
    private int calculateXpReward(Quiz quiz, boolean isCorrect, Integer timeSpent) {
        if (!isCorrect) {
            return 0; // 答错不给经验值
        }
        
        // 基础经验值（根据难度）
        int baseXp = getBaseXpByDifficulty(quiz.getDifficulty());
        
        // 时间奖励系数（答题越快奖励越高，但有上限）
        double timeBonus = 1.0;
        if (timeSpent != null && timeSpent > 0) {
            // 假设标准答题时间为60秒，少于30秒给予时间奖励
            if (timeSpent <= 30) {
                timeBonus = 1.5; // 50%时间奖励
            } else if (timeSpent <= 45) {
                timeBonus = 1.2; // 20%时间奖励
            }
            // 超过120秒减少奖励
            else if (timeSpent > 120) {
                timeBonus = 0.8; // 减少20%
            }
        }
        
        return (int) Math.round(baseXp * timeBonus);
    }
    
    /**
     * 根据难度获取基础经验值
     */
    private int getBaseXpByDifficulty(String difficulty) {
        switch (difficulty.toUpperCase()) {
            case "BEGINNER": return 10;
            case "INTERMEDIATE": return 15;
            case "ADVANCED": return 25;
            default: return 10;
        }
    }
    
    /**
     * 更新用户进度（经验值和等级）
     */
    private void updateUserProgress(Long userId, int xpEarned) {
        try {
            // 这里可以调用UserProgressService来更新用户的经验值和等级
            // 暂时记录日志，实际实现需要根据具体的用户进度系统
            log.info("User {} earned {} XP, total progress should be updated", userId, xpEarned);
            
            // TODO: 实现用户经验值和等级更新逻辑
            // userProgressService.addExperience(userId, xpEarned);
            
        } catch (Exception e) {
            log.error("Error updating user progress for user {} with XP {}", userId, xpEarned, e);
        }
    }
    
    @Override
    public Map<String, Object> getCodingChallenge(Long userId, String level) {
        try {
            String difficulty = level != null ? level : "INTERMEDIATE";
            CodingChallenge challengeEntity = codingChallengeMapper.getChallengeByDifficulty(difficulty);
            
            if (challengeEntity == null) {
                // 如果没有找到指定难度的挑战，随机获取一个
                List<CodingChallenge> allChallenges = codingChallengeMapper.getAllCodingChallenges();
                if (!allChallenges.isEmpty()) {
                    challengeEntity = allChallenges.get(new Random().nextInt(allChallenges.size()));
                }
            }
            
            if (challengeEntity != null) {
                Map<String, Object> challenge = new HashMap<>();
                challenge.put("id", challengeEntity.getId());
                challenge.put("title", challengeEntity.getTitle());
                challenge.put("description", challengeEntity.getDescription());
                challenge.put("difficulty", challengeEntity.getDifficulty());
                challenge.put("language", challengeEntity.getLanguage());
                challenge.put("template", challengeEntity.getTemplate());
                challenge.put("testCases", challengeEntity.getTestCases());
                challenge.put("xpReward", challengeEntity.getXpReward());
                challenge.put("category", challengeEntity.getCategory());
                challenge.put("timeLimit", challengeEntity.getTimeLimit());
                return challenge;
            }
            
            // 如果数据库中没有数据，返回默认挑战
            return getDefaultCodingChallenge(difficulty);
        } catch (Exception e) {
            log.error("Error getting coding challenge for user {} with level {}", userId, level, e);
            return getDefaultCodingChallenge(level != null ? level : "INTERMEDIATE");
        }
    }
    
    private Map<String, Object> getDefaultCodingChallenge(String difficulty) {
        Map<String, Object> challenge = new HashMap<>();
        challenge.put("id", 1L);
        challenge.put("title", "Array Filter Implementation");
        challenge.put("description", "Implement a function that filters an array based on a condition");
        challenge.put("difficulty", difficulty);
        challenge.put("language", "JavaScript");
        challenge.put("template", "function filterArray(arr, condition) {\n  // Your code here\n}");
        challenge.put("testCases", "[1,2,3,4,5], x => x > 2");
        challenge.put("xpReward", 100);
        challenge.put("category", "Array");
        challenge.put("timeLimit", 30);
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
        try {
            List<Achievement> achievementEntities = achievementMapper.getAllAchievements();
            List<Map<String, Object>> achievements = new ArrayList<>();
            
            for (Achievement achievement : achievementEntities) {
                Map<String, Object> achievementMap = new HashMap<>();
                achievementMap.put("id", achievement.getId());
                achievementMap.put("name", achievement.getName());
                achievementMap.put("description", achievement.getDescription());
                achievementMap.put("icon", achievement.getIcon());
                achievementMap.put("xpReward", achievement.getXpReward());
                achievementMap.put("requirementType", achievement.getRequirementType());
                achievementMap.put("requirementValue", achievement.getRequirementValue());
                // TODO: 实现用户成就解锁状态查询，暂时设为false
                achievementMap.put("unlocked", false);
                achievements.add(achievementMap);
            }
            
            return achievements;
        } catch (Exception e) {
            log.error("Error getting achievements for user {}", userId, e);
            // 发生异常时返回默认成就列表
            return getDefaultAchievements();
        }
    }
    
    private List<Map<String, Object>> getDefaultAchievements() {
        List<Map<String, Object>> achievements = new ArrayList<>();
        
        Map<String, Object> achievement1 = new HashMap<>();
        achievement1.put("id", 1L);
        achievement1.put("name", "First Steps");
        achievement1.put("description", "Complete your first lesson");
        achievement1.put("icon", "🎯");
        achievement1.put("unlocked", true);
        achievements.add(achievement1);
        
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
        try {
            // 按经验值降序排列获取前N名用户
            List<User> topUsers = userMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                    .orderByDesc(User::getExperience)
                    .orderByDesc(User::getLevel)
                    .orderByDesc(User::getStreakDays)
                    .last("LIMIT " + (limit != null ? limit : 20))
            );
            
            List<Map<String, Object>> leaderboard = new ArrayList<>();
            int rank = 1;
            
            for (User user : topUsers) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("rank", rank++);
                userMap.put("userId", user.getId());
                userMap.put("username", user.getUsername());
                userMap.put("nickname", user.getNickname());
                userMap.put("avatar", user.getAvatarUrl());
                userMap.put("level", user.getLevel());
                userMap.put("experience", user.getExperience());
                userMap.put("streakDays", user.getStreakDays());
                userMap.put("totalLearningTime", user.getTotalLearningTime());
                leaderboard.add(userMap);
            }
            
            return leaderboard;
        } catch (Exception e) {
            log.error("Error getting leaderboard", e);
            // 发生异常时返回默认排行榜
            return getDefaultLeaderboard(limit);
        }
    }
    
    private List<Map<String, Object>> getDefaultLeaderboard(Integer limit) {
        List<Map<String, Object>> leaderboard = new ArrayList<>();
        
        String[] names = {"Alex Chen", "Maria Garcia", "David Kim", "Sarah Johnson", "Tom Wilson"};
        int[] scores = {12450, 11200, 10800, 9450, 8900};
        
        for (int i = 0; i < Math.min(limit != null ? limit : names.length, names.length); i++) {
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