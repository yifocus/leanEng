package com.devenglish.controller;

import com.devenglish.common.Result;
import com.devenglish.entity.Vocabulary;
import com.devenglish.service.VocabularyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vocabulary")
@RequiredArgsConstructor
@Tag(name = "词汇学习管理")
@CrossOrigin
public class VocabularyController {
    
    private final VocabularyService vocabularyService;
    
    @GetMapping("/list")
    @Operation(summary = "获取词汇列表")
    public Result<List<Vocabulary>> getVocabularyList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        return Result.success(vocabularyService.getVocabularyList(category, keyword, page, size));
    }
    
    @GetMapping("/categories")
    @Operation(summary = "获取词汇分类")
    public Result<List<Map<String, Object>>> getCategories() {
        return Result.success(vocabularyService.getCategories());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取词汇详情")
    public Result<Vocabulary> getVocabularyDetail(@PathVariable Long id) {
        return Result.success(vocabularyService.getById(id));
    }
    
    @PostMapping("/learn/{id}")
    @Operation(summary = "标记已学习")
    public Result<Void> markAsLearned(@PathVariable Long id, @RequestParam Long userId) {
        vocabularyService.markAsLearned(id, userId);
        return Result.success();
    }
    
    @GetMapping("/daily")
    @Operation(summary = "获取每日词汇")
    public Result<List<Vocabulary>> getDailyVocabulary(@RequestParam Long userId) {
        return Result.success(vocabularyService.getDailyVocabulary(userId));
    }
    
    @GetMapping("/search")
    @Operation(summary = "搜索词汇")
    public Result<List<Vocabulary>> searchVocabulary(@RequestParam String query) {
        return Result.success(vocabularyService.searchVocabulary(query));
    }
    
    /*@PutMapping("/{id}")
    @Operation(summary = "更新词汇定义")
    public Result<Void> updateVocabulary(@PathVariable Long id, @RequestBody Vocabulary vocabulary) {
        vocabulary.setId(id);
        vocabularyService.updateById(vocabulary);
        return Result.success();
    }*/
    
    @GetMapping("/parents")
    @Operation(summary = "获取父词汇列表")
    public Result<List<Vocabulary>> getParentVocabulary(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        return Result.success(vocabularyService.getParentVocabulary(page, size));
    }
    
    @GetMapping("/children/{parentId}")
    @Operation(summary = "根据父词汇ID获取子词汇列表")
    public Result<List<Vocabulary>> getChildrenVocabulary(@PathVariable Long parentId) {
        return Result.success(vocabularyService.getChildrenByParentId(parentId));
    }
    
    @GetMapping("/detail/{id}")
    @Operation(summary = "获取词汇完整详情")
    public Result<Vocabulary> getVocabularyFullDetail(@PathVariable Long id) {
        return Result.success(vocabularyService.getVocabularyFullDetail(id));
    }
}