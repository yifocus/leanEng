package com.devenglish.service.impl;

import com.devenglish.entity.Vocabulary;
import com.devenglish.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VocabularyServiceImpl implements VocabularyService {
    
    @Override
    public List<Vocabulary> getVocabularyList(String category, String keyword, Integer page, Integer size) {
        // 返回模拟数据
        List<Vocabulary> list = new ArrayList<>();
        Vocabulary vocab = new Vocabulary();
        vocab.setId(1L);
        vocab.setTerm("Microservices");
        vocab.setDefinition("An architectural style that structures an application as a collection of services");
        vocab.setCategory("Architecture");
        vocab.setTranslation("微服务");
        list.add(vocab);
        return list;
    }
    
    @Override
    public List<Map<String, Object>> getCategories() {
        List<Map<String, Object>> categories = new ArrayList<>();
        Map<String, Object> cat1 = new HashMap<>();
        cat1.put("name", "Architecture");
        cat1.put("count", 50);
        categories.add(cat1);
        return categories;
    }
    
    @Override
    public Vocabulary getById(Long id) {
        Vocabulary vocab = new Vocabulary();
        vocab.setId(id);
        vocab.setTerm("Docker");
        vocab.setDefinition("A platform for developing, shipping, and running applications in containers");
        return vocab;
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
        return getVocabularyList(null, query, 1, 10);
    }
}