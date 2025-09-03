package com.devenglish.service;

import com.devenglish.dto.ChatMessage;
import com.devenglish.dto.ScenarioResponse;
import java.util.List;
import java.util.Map;

public interface ScenarioService {
    
    List<ScenarioResponse> getScenarioList();
    
    ScenarioResponse getScenarioDetail(Long id);
    
    ChatMessage processMessage(ChatMessage message);
    
    List<ChatMessage> getChatHistory(Long scenarioId, Long userId);
    
    Map<String, Object> completeScenario(Long scenarioId, Long userId);
    
    List<String> getResponseTemplates(String scenario);
}