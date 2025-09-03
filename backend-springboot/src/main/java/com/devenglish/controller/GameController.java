package com.devenglish.controller;

import com.devenglish.common.Result;
import com.devenglish.dto.QuizQuestion;
import com.devenglish.dto.QuizSubmission;
import com.devenglish.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
@Api(tags = "游戏化学习")
@CrossOrigin
public class GameController {
    
    private final GameService gameService;
    
    @GetMapping("/quiz/next")
    @ApiOperation("获取下一道测验题")
    public Result<QuizQuestion> getNextQuizQuestion(
            @RequestParam Long userId,
            @RequestParam(required = false) String difficulty) {
        return Result.success(gameService.getNextQuizQuestion(userId, difficulty));
    }
    
    @PostMapping("/quiz/submit")
    @ApiOperation("提交测验答案")
    public Result<Map<String, Object>> submitQuizAnswer(@RequestBody QuizSubmission submission) {
        return Result.success(gameService.submitQuizAnswer(submission));
    }
    
    @GetMapping("/coding-challenge")
    @ApiOperation("获取编程挑战")
    public Result<Map<String, Object>> getCodingChallenge(
            @RequestParam Long userId,
            @RequestParam(required = false) String level) {
        return Result.success(gameService.getCodingChallenge(userId, level));
    }
    
    @PostMapping("/coding-challenge/submit")
    @ApiOperation("提交编程挑战答案")
    public Result<Map<String, Object>> submitCodingChallenge(
            @RequestParam Long challengeId,
            @RequestParam Long userId,
            @RequestBody String code) {
        return Result.success(gameService.submitCodingChallenge(challengeId, userId, code));
    }
    
    @GetMapping("/achievements")
    @ApiOperation("获取成就列表")
    public Result<List<Map<String, Object>>> getAchievements(@RequestParam Long userId) {
        return Result.success(gameService.getAchievements(userId));
    }
    
    @PostMapping("/achievements/{achievementId}/unlock")
    @ApiOperation("解锁成就")
    public Result<Map<String, Object>> unlockAchievement(
            @PathVariable Long achievementId,
            @RequestParam Long userId) {
        return Result.success(gameService.unlockAchievement(achievementId, userId));
    }
    
    @GetMapping("/leaderboard")
    @ApiOperation("获取排行榜")
    public Result<List<Map<String, Object>>> getLeaderboard(
            @RequestParam(defaultValue = "weekly") String period,
            @RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(gameService.getLeaderboard(period, limit));
    }
}