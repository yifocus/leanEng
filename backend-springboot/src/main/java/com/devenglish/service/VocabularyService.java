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
}