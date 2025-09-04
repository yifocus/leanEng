package com.devenglish.controller;

import com.devenglish.common.Result;
import com.devenglish.dto.DailyTaskResponse;
import com.devenglish.dto.ProgressResponse;
import com.devenglish.dto.StatisticsResponse;
import com.devenglish.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
@Tag(name = "学习进度管理")
@CrossOrigin
public class ProgressController {
    
    private final ProgressService progressService;
    
    @GetMapping("/dashboard")
    @Operation(summary = "获取仪表板数据")
    public Result<Map<String, Object>> getDashboardData(@RequestParam Long userId) {
        return Result.success(progressService.getDashboardData(userId));
    }
    
    @GetMapping("/statistics")
    @Operation(summary = "获取学习统计")
    public Result<StatisticsResponse> getStatistics(@RequestParam Long userId) {
        return Result.success(progressService.getStatistics(userId));
    }
    
    @GetMapping("/daily-tasks")
    @Operation(summary = "获取每日任务")
    public Result<List<DailyTaskResponse>> getDailyTasks(@RequestParam Long userId) {
        return Result.success(progressService.getDailyTasks(userId));
    }
    
    @PostMapping("/daily-tasks/{taskId}/complete")
    @Operation(summary = "完成每日任务")
    public Result<Map<String, Object>> completeTask(
            @PathVariable Long taskId,
            @RequestParam Long userId) {
        return Result.success(progressService.completeTask(taskId, userId));
    }
    
    @GetMapping("/learning-path")
    @Operation(summary = "获取学习路径")
    public Result<List<Map<String, Object>>> getLearningPath(@RequestParam Long userId) {
        return Result.success(progressService.getLearningPath(userId));
    }
    
    @PostMapping("/update-streak")
    @Operation(summary = "更新连续学习天数")
    public Result<Integer> updateStreak(@RequestParam Long userId) {
        return Result.success(progressService.updateStreak(userId));
    }
    
    @PostMapping("/add-experience")
    @Operation(summary = "增加经验值")
    public Result<Map<String, Object>> addExperience(
            @RequestParam Long userId,
            @RequestParam Integer points) {
        return Result.success(progressService.addExperience(userId, points));
    }
}