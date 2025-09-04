package com.devenglish.service;

import com.devenglish.entity.Vocabulary;
import java.util.List;
import java.util.Map;

public interface VocabularyService {
    
    List<Vocabulary> getVocabularyList(String category, String keyword, Integer page, Integer size);
    
    List<Map<String, Object>> getCategories();
    
    Vocabulary getById(Long id);
    
    void markAsLearned(Long id, Long userId);
    
    List<Vocabulary> getDailyVocabulary(Long userId);
    
    List<Vocabulary> searchVocabulary(String query);
    
    void updateById(Vocabulary vocabulary);
    
    /**
     * 获取父词汇列表
     * @param page 页码
     * @param size 页面大小
     * @return 父词汇列表
     */
    List<Vocabulary> getParentVocabulary(Integer page, Integer size);
    
    /**
     * 根据父词汇ID获取子词汇列表
     * @param parentId 父词汇ID
     * @return 子词汇列表
     */
    List<Vocabulary> getChildrenByParentId(Long parentId);
    
    /**
     * 获取词汇完整详情
     * @param id 词汇ID
     * @return 词汇完整详情
     */
    Vocabulary getVocabularyFullDetail(Long id);
}