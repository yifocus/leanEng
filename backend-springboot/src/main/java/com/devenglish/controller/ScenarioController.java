package com.devenglish.controller;

import com.devenglish.common.Result;
import com.devenglish.dto.ChatMessage;
import com.devenglish.dto.ScenarioResponse;
import com.devenglish.service.ScenarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scenario")
@RequiredArgsConstructor
@Api(tags = "场景对话管理")
@CrossOrigin
public class ScenarioController {
    
    private final ScenarioService scenarioService;
    
    @GetMapping("/list")
    @ApiOperation("获取场景列表")
    public Result<List<ScenarioResponse>> getScenarioList() {
        return Result.success(scenarioService.getScenarioList());
    }
    
    @GetMapping("/{id}")
    @ApiOperation("获取场景详情")
    public Result<ScenarioResponse> getScenarioDetail(@PathVariable Long id) {
        return Result.success(scenarioService.getScenarioDetail(id));
    }
    
    @PostMapping("/chat")
    @ApiOperation("发送对话消息")
    public Result<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
        return Result.success(scenarioService.processMessage(message));
    }
    
    @GetMapping("/{scenarioId}/history")
    @ApiOperation("获取对话历史")
    public Result<List<ChatMessage>> getChatHistory(
            @PathVariable Long scenarioId,
            @RequestParam(required = false, defaultValue = "0") Long userId) {
        return Result.success(scenarioService.getChatHistory(scenarioId, userId));
    }
    
    @PostMapping("/{scenarioId}/complete")
    @ApiOperation("完成场景练习")
    public Result<Map<String, Object>> completeScenario(
            @PathVariable Long scenarioId,
            @RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.getOrDefault("userId", 0).toString());
        return Result.success(scenarioService.completeScenario(scenarioId, userId));
    }
    
    @GetMapping("/{scenarioId}/templates")
    @ApiOperation("获取场景对话模板")
    public Result<List<String>> getResponseTemplates(@PathVariable Long scenarioId) {
        String scenarioName = getScenarioNameById(scenarioId);
        return Result.success(scenarioService.getResponseTemplates(scenarioName));
    }
    
    @GetMapping("/categories")
    @ApiOperation("获取场景分类")
    public Result<List<String>> getCategories() {
        List<String> categories = Arrays.asList(
            "Agile/Scrum",
            "Development", 
            "Architecture",
            "Business",
            "Quality Assurance",
            "DevOps",
            "Team Collaboration"
        );
        return Result.success(categories);
    }
    
    @GetMapping("/recommended")
    @ApiOperation("获取推荐场景")
    public Result<List<ScenarioResponse>> getRecommendedScenarios(
            @RequestParam(required = false, defaultValue = "0") Long userId) {
        List<ScenarioResponse> allScenarios = scenarioService.getScenarioList();
        List<ScenarioResponse> recommended = allScenarios.size() > 3 
            ? allScenarios.subList(0, 3) 
            : allScenarios;
        return Result.success(recommended);
    }
    
    @GetMapping("/statistics")
    @ApiOperation("获取练习统计")
    public Result<Map<String, Object>> getStatistics(
            @RequestParam(required = false, defaultValue = "0") Long userId) {
        List<Map<String, Object>> weeklyProgress = new ArrayList<>();
        Map<String, Object> mon = new HashMap<>();
        mon.put("day", "Mon");
        mon.put("minutes", 30);
        weeklyProgress.add(mon);
        
        Map<String, Object> tue = new HashMap<>();
        tue.put("day", "Tue");
        tue.put("minutes", 45);
        weeklyProgress.add(tue);
        
        Map<String, Object> wed = new HashMap<>();
        wed.put("day", "Wed");
        wed.put("minutes", 20);
        weeklyProgress.add(wed);
        
        Map<String, Object> thu = new HashMap<>();
        thu.put("day", "Thu");
        thu.put("minutes", 60);
        weeklyProgress.add(thu);
        
        Map<String, Object> fri = new HashMap<>();
        fri.put("day", "Fri");
        fri.put("minutes", 25);
        weeklyProgress.add(fri);
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalScenarios", 8);
        stats.put("completedScenarios", 3);
        stats.put("totalPracticeTime", 180);
        stats.put("averageScore", 82);
        stats.put("mostPracticedCategory", "Agile/Scrum");
        stats.put("weeklyProgress", weeklyProgress);
        
        return Result.success(stats);
    }
    
    private String getScenarioNameById(Long id) {
        switch (id.intValue()) {
            case 1: return "sprint_planning";
            case 2: return "daily_standup";
            case 3: return "code_review";
            case 4: return "client_meeting";
            case 5: return "technical_discussion";
            default: return "general";
        }
    }
}