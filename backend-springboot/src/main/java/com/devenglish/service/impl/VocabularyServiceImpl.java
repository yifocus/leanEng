package com.devenglish.service.impl;

import com.devenglish.entity.Vocabulary;
import com.devenglish.mapper.VocabularyMapper;
import com.devenglish.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VocabularyServiceImpl implements VocabularyService {
    
    private final VocabularyMapper vocabularyMapper;
    
    @Override
    public List<Vocabulary> getVocabularyList(String category, String keyword, Integer page, Integer size) {
        int offset = (page - 1) * size;
        
        if (category != null && !category.isEmpty() && keyword != null && !keyword.isEmpty()) {
            return vocabularyMapper.findByCategoryAndKeyword(category, keyword, offset, size);
        } else if (category != null && !category.isEmpty()) {
            return vocabularyMapper.findByCategory(category, offset, size);
        } else if (keyword != null && !keyword.isEmpty()) {
            return vocabularyMapper.findByKeyword(keyword, offset, size);
        } else {
            return vocabularyMapper.findAll(offset, size);
        }
    }
    
    @Override
    public List<Map<String, Object>> getCategories() {
        return vocabularyMapper.getCategories();
    }
    
    @Override
    public Vocabulary getById(Long id) {
        return vocabularyMapper.selectById(id);
    }
    
    @Override
    public void markAsLearned(Long id, Long userId) {
        log.info("Marking vocabulary {} as learned for user {}", id, userId);
    }
    
    @Override
    public List<Vocabulary> getDailyVocabulary(Long userId) {
        return getVocabularyList(null, null, 1, 5);
    }
    
    @Override
    public List<Vocabulary> searchVocabulary(String query) {
        return vocabularyMapper.searchVocabulary(query, 10);
    }
}