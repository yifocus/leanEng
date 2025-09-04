package com.devenglish.service.impl;

import com.devenglish.dto.ChatMessage;
import com.devenglish.dto.ScenarioDetailResponse;
import com.devenglish.dto.ScenarioResponse;
import com.devenglish.entity.Scenario;
import com.devenglish.mapper.ScenarioMapper;
import com.devenglish.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScenarioServiceImpl implements ScenarioService {
    
    private final ScenarioMapper scenarioMapper;
    
    // 预设的外包场景对话数据
    private static final Map<String, List<String>> SCENARIO_RESPONSES = new HashMap<>();
    private static final Map<String, List<String>> SCENARIO_TEMPLATES = new HashMap<>();
    
    static {
        // Sprint Planning场景的预设回复
        SCENARIO_RESPONSES.put("sprint_planning", Arrays.asList(
            "Based on our velocity from last sprint, I think we can commit to 30 story points for this sprint.",
            "The authentication module has some dependencies on the API gateway. We should prioritize the gateway first.",
            "I'll need about 2 days for the user registration feature, including unit tests and documentation.",
            "Can we break down this user story into smaller tasks? It seems too large for a single sprint.",
            "I've identified a potential blocker with the third-party payment integration. We need to get API credentials first.",
            "The acceptance criteria for this story aren't clear. Could you provide more specific requirements?"
        ));
        
        // Daily Standup场景的预设回复
        SCENARIO_RESPONSES.put("daily_standup", Arrays.asList(
            "Yesterday I completed the login API endpoint and started working on the password reset feature.",
            "Today I'll be focusing on integrating the email service for user notifications.",
            "I'm facing a blocker with the database migration. The staging environment seems to be down.",
            "I paired with Sarah on the code review and we found some performance improvements.",
            "No blockers at the moment. I should be able to finish the task by end of day.",
            "I'll need some help with the React component. Maybe we can do a quick knowledge sharing session?"
        ));
        
        // Code Review场景的预设回复
        SCENARIO_RESPONSES.put("code_review", Arrays.asList(
            "I've refactored this method to follow the single responsibility principle. It's much cleaner now.",
            "Good catch on the potential null pointer exception. I'll add proper validation.",
            "I think we should extract this logic into a separate service class for better maintainability.",
            "The test coverage for this module is currently at 85%. I'll add more edge cases.",
            "I've used the strategy pattern here to make the code more extensible.",
            "Should we consider caching this database query? It's called frequently with the same parameters."
        ));
        
        // Client Meeting场景的预设回复
        SCENARIO_RESPONSES.put("client_meeting", Arrays.asList(
            "We've completed 80% of the planned features for this milestone. The remaining items are on track.",
            "The performance optimization resulted in a 40% reduction in page load time.",
            "We encountered some unexpected technical debt, but we've addressed it without impacting the timeline.",
            "I'd recommend implementing this as a phased rollout to minimize risk.",
            "The user feedback from the beta testing has been very positive. Here are the key insights...",
            "We can accommodate that change request, but it will require an additional week of development time."
        ));
        
        // Technical Discussion场景的预设回复
        SCENARIO_RESPONSES.put("technical_discussion", Arrays.asList(
            "I suggest using microservices architecture for better scalability and maintainability.",
            "We should implement circuit breakers to handle service failures gracefully.",
            "The database sharding strategy will help us handle the increased load.",
            "I've researched several solutions, and I believe Kubernetes would be the best fit for our needs.",
            "We need to consider GDPR compliance in our data handling approach.",
            "The API rate limiting will prevent abuse and ensure fair usage across all clients."
        ));
    }
    
    @Override
    public List<ScenarioResponse> getScenarioList() {
        try {
            List<Scenario> scenarioEntities = scenarioMapper.getAllScenarios();
            List<ScenarioResponse> scenarios = new ArrayList<>();
            
            for (Scenario scenario : scenarioEntities) {
                scenarios.add(ScenarioResponse.builder()
                        .id(scenario.getId())
                        .name(scenario.getName())
                        .description(scenario.getDescription())
                        .avatar(scenario.getAvatar())
                        .role(scenario.getRole())
                        .difficulty(scenario.getDifficulty())
                        .category(scenario.getCategory())
                        .estimatedTime(scenario.getEstimatedTime())
                        .build());
            }
            
            return scenarios;
        } catch (Exception e) {
            log.error("Error getting scenario list", e);
            // 发生异常时返回默认场景列表
            return getDefaultScenarioList();
        }
    }
    
    private List<ScenarioResponse> getDefaultScenarioList() {
        List<ScenarioResponse> scenarios = new ArrayList<>();
        
        scenarios.add(ScenarioResponse.builder()
                .id(1L)
                .name("Sprint Planning Meeting")
                .description("Plan and estimate tasks for the upcoming sprint with your distributed team")
                .avatar("📋")
                .role("Scrum Master / Product Owner")
                .difficulty("INTERMEDIATE")
                .category("Agile/Scrum")
                .estimatedTime(20)
                .build());
        
        scenarios.add(ScenarioResponse.builder()
                .id(2L)
                .name("Daily Stand-up Meeting")
                .description("Report your progress and discuss blockers in a daily scrum meeting")
                .avatar("🎯")
                .role("Development Team")
                .difficulty("BEGINNER")
                .category("Agile/Scrum")
                .estimatedTime(10)
                .build());
        
        scenarios.add(ScenarioResponse.builder()
                .id(3L)
                .name("Code Review Session")
                .description("Present your code changes and respond to feedback from senior developers")
                .avatar("👨‍💻")
                .role("Senior Developer")
                .difficulty("INTERMEDIATE")
                .category("Development")
                .estimatedTime(25)
                .build());
        
        scenarios.add(ScenarioResponse.builder()
                .id(4L)
                .name("Client Requirements Discussion")
                .description("Clarify project requirements and scope with an international client")
                .avatar("🤝")
                .role("Client Representative")
                .difficulty("ADVANCED")
                .category("Business")
                .estimatedTime(30)
                .build());
        
        scenarios.add(ScenarioResponse.builder()
                .id(5L)
                .name("Technical Architecture Discussion")
                .description("Discuss system design and architecture decisions with the tech lead")
                .avatar("🏗️")
                .role("Technical Architect")
                .difficulty("ADVANCED")
                .category("Architecture")
                .estimatedTime(35)
                .build());
        
        scenarios.add(ScenarioResponse.builder()
                .id(6L)
                .name("Bug Triage Meeting")
                .description("Prioritize and assign bugs reported by QA team")
                .avatar("🐛")
                .role("QA Lead")
                .difficulty("INTERMEDIATE")
                .category("Quality Assurance")
                .estimatedTime(15)
                .build());
        
        scenarios.add(ScenarioResponse.builder()
                .id(7L)
                .name("Release Planning")
                .description("Plan the deployment strategy and discuss rollback procedures")
                .avatar("🚀")
                .role("DevOps Engineer")
                .difficulty("ADVANCED")
                .category("DevOps")
                .estimatedTime(25)
                .build());
        
        scenarios.add(ScenarioResponse.builder()
                .id(8L)
                .name("Remote Team Sync")
                .description("Coordinate with offshore team members across different time zones")
                .avatar("🌏")
                .role("Remote Team Lead")
                .difficulty("INTERMEDIATE")
                .category("Team Collaboration")
                .estimatedTime(20)
                .build());
        
        return scenarios;
    }
    
    @Override
    public ScenarioResponse getScenarioDetail(Long id) {
        // 返回更详细的场景信息
        if (id == 1L) {
            return ScenarioResponse.builder()
                    .id(id)
                    .name("Sprint Planning Meeting")
                    .description("You're participating in a sprint planning meeting for a new e-commerce platform. " +
                            "The team needs to estimate user stories, identify dependencies, and commit to sprint goals.")
                    .avatar("📋")
                    .role("Scrum Master / Product Owner")
                    .difficulty("INTERMEDIATE")
                    .category("Agile/Scrum")
                    .estimatedTime(20)
                    .build();
        }
        
        return getScenarioList().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public ChatMessage processMessage(ChatMessage message) {
        ChatMessage response = new ChatMessage();
        response.setScenarioId(message.getScenarioId());
        response.setUserId(0L); // System/AI user
        response.setRole("AI");
        response.setTimestamp(System.currentTimeMillis());
        
        // 根据场景ID和消息内容生成智能回复
        String userMessage = message.getMessage().toLowerCase();
        String aiResponse = generateSmartResponse(message.getScenarioId(), userMessage);
        
        response.setMessage(aiResponse);
        return response;
    }
    
    private String generateSmartResponse(Long scenarioId, String userMessage) {
        // 根据关键词匹配生成回复
        if (userMessage.contains("estimate") || userMessage.contains("story point")) {
            return "That's a good point about estimation. Based on the complexity you've described, " +
                   "I would suggest breaking it down into smaller tasks. What do you think about allocating " +
                   "5 story points for the backend API and 3 for the frontend implementation?";
        }
        
        if (userMessage.contains("blocker") || userMessage.contains("issue")) {
            return "I understand there's a blocker. Let's discuss this in more detail. " +
                   "Can you elaborate on what specific technical challenge you're facing? " +
                   "Perhaps we can find a workaround or reprioritize the tasks.";
        }
        
        if (userMessage.contains("deadline") || userMessage.contains("timeline")) {
            return "Regarding the timeline, we need to be realistic about what we can achieve. " +
                   "Based on our current velocity, I think we can deliver the MVP by the end of this sprint. " +
                   "However, we might need to push some nice-to-have features to the next iteration. Does that work for you?";
        }
        
        if (userMessage.contains("test") || userMessage.contains("quality")) {
            return "Quality is definitely a priority. I suggest we allocate at least 20% of our sprint capacity " +
                   "for testing and bug fixes. We should also ensure that all new features have comprehensive " +
                   "unit tests and integration tests. What's your take on implementing automated testing?";
        }
        
        if (userMessage.contains("help") || userMessage.contains("support")) {
            return "I'd be happy to help! We can schedule a pair programming session or a knowledge transfer meeting. " +
                   "Which specific area would you like assistance with? I can also prepare some documentation " +
                   "to help you get up to speed more quickly.";
        }
        
        // 默认回复 - 根据场景选择合适的回复
        List<String> responses = getResponsesForScenario(scenarioId);
        return responses.get(new Random().nextInt(responses.size()));
    }
    
    private List<String> getResponsesForScenario(Long scenarioId) {
        // 根据场景ID返回对应的预设回复
        switch (scenarioId.intValue()) {
            case 1:
                return SCENARIO_RESPONSES.get("sprint_planning");
            case 2:
                return SCENARIO_RESPONSES.get("daily_standup");
            case 3:
                return SCENARIO_RESPONSES.get("code_review");
            case 4:
                return SCENARIO_RESPONSES.get("client_meeting");
            case 5:
                return SCENARIO_RESPONSES.get("technical_discussion");
            default:
                return Arrays.asList(
                    "That's an interesting point. Could you elaborate on that?",
                    "I agree with your approach. Let's move forward with this plan.",
                    "We should consider the impact on the overall architecture.",
                    "Let me check with the team and get back to you on this."
                );
        }
    }
    
    @Override
    public List<ChatMessage> getChatHistory(Long scenarioId, Long userId) {
        // 返回用户在特定场景的对话历史
        List<ChatMessage> history = new ArrayList<>();
        
        // 添加初始系统消息
        ChatMessage systemMsg = new ChatMessage();
        systemMsg.setScenarioId(scenarioId);
        systemMsg.setRole("system");
        systemMsg.setMessage(getScenarioIntroduction(scenarioId));
        systemMsg.setTimestamp(System.currentTimeMillis() - 60000);
        history.add(systemMsg);
        
        // 添加AI开场白
        ChatMessage aiGreeting = new ChatMessage();
        aiGreeting.setScenarioId(scenarioId);
        aiGreeting.setRole("AI");
        aiGreeting.setMessage(getScenarioGreeting(scenarioId));
        aiGreeting.setTimestamp(System.currentTimeMillis() - 30000);
        history.add(aiGreeting);
        
        return history;
    }
    
    private String getScenarioIntroduction(Long scenarioId) {
        switch (scenarioId.intValue()) {
            case 1:
                return "Welcome to Sprint Planning Meeting. You'll be discussing the upcoming sprint goals, " +
                       "estimating user stories, and identifying potential blockers with your team.";
            case 2:
                return "Daily Stand-up Meeting started. Share what you did yesterday, what you'll do today, " +
                       "and any blockers you're facing.";
            case 3:
                return "Code Review Session. Present your code changes and be ready to explain your " +
                       "implementation decisions and respond to feedback.";
            case 4:
                return "Client Requirements Discussion. Clarify project requirements, scope, and timeline " +
                       "with the client representative.";
            default:
                return "Welcome to the scenario. Let's begin the discussion.";
        }
    }
    
    private String getScenarioGreeting(Long scenarioId) {
        switch (scenarioId.intValue()) {
            case 1:
                return "Good morning team! Let's start our sprint planning. We have several user stories " +
                       "in the backlog. Which one would you like to discuss first?";
            case 2:
                return "Good morning everyone! Let's quickly go through our updates. " +
                       "Can you share what you accomplished yesterday?";
            case 3:
                return "Hi! I see you've submitted a pull request for the authentication module. " +
                       "Can you walk me through the main changes you've made?";
            case 4:
                return "Hello! Thank you for joining the call. I'd like to discuss the requirements " +
                       "for the new feature we're planning. Do you have any initial questions?";
            default:
                return "Hello! Let's begin our discussion. How can I help you today?";
        }
    }
    
    @Override
    public Map<String, Object> completeScenario(Long scenarioId, Long userId) {
        Map<String, Object> result = new HashMap<>();
        
        // 计算场景完成的得分和反馈
        int score = calculateScenarioScore();
        result.put("score", score);
        result.put("xpEarned", score * 2);
        result.put("feedback", generateFeedback(score));
        result.put("improvements", getImprovementSuggestions(score));
        result.put("nextScenario", getNextRecommendedScenario(scenarioId));
        
        return result;
    }
    
    private int calculateScenarioScore() {
        // 简化的评分逻辑，实际应该基于对话质量、语法、词汇等
        return 75 + new Random().nextInt(25);
    }
    
    private String generateFeedback(int score) {
        if (score >= 90) {
            return "Excellent! Your communication was clear, professional, and technically accurate.";
        } else if (score >= 75) {
            return "Good job! You handled the scenario well with minor areas for improvement.";
        } else if (score >= 60) {
            return "Fair performance. Consider practicing more technical vocabulary and common phrases.";
        } else {
            return "Keep practicing! Focus on clarity and using appropriate technical terminology.";
        }
    }
    
    private List<String> getImprovementSuggestions(int score) {
        List<String> suggestions = new ArrayList<>();
        
        if (score < 90) {
            suggestions.add("Use more specific technical terms when discussing implementation details");
            suggestions.add("Practice active listening and ask clarifying questions");
        }
        if (score < 75) {
            suggestions.add("Work on structuring your responses more clearly");
            suggestions.add("Review common phrases used in agile meetings");
        }
        if (score < 60) {
            suggestions.add("Study basic project management vocabulary");
            suggestions.add("Practice speaking more fluently without long pauses");
        }
        
        return suggestions;
    }
    
    private Long getNextRecommendedScenario(Long currentScenarioId) {
        // 推荐下一个场景
        return currentScenarioId < 8 ? currentScenarioId + 1 : 1L;
    }
    
    @Override
    public List<String> getResponseTemplates(String scenario) {
        // 返回场景相关的回复模板
        List<String> templates = new ArrayList<>();
        
        if (scenario.contains("sprint") || scenario.contains("planning")) {
            templates.add("I think we can complete this in [X] story points");
            templates.add("This task depends on [dependency], so we should prioritize it");
            templates.add("Based on our velocity, we can commit to [X] points this sprint");
            templates.add("I'll need clarification on the acceptance criteria");
            templates.add("This seems like a good candidate for the next sprint");
        } else if (scenario.contains("standup") || scenario.contains("daily")) {
            templates.add("Yesterday I completed [task] and started working on [next task]");
            templates.add("Today I'll be focusing on [specific task]");
            templates.add("I'm facing a blocker with [issue description]");
            templates.add("No blockers, I'm on track to finish [task] by [time]");
            templates.add("I might need help with [specific area]");
        } else if (scenario.contains("review") || scenario.contains("code")) {
            templates.add("I've refactored this to improve [specific aspect]");
            templates.add("Good point, I'll add [improvement] to address that");
            templates.add("The test coverage is currently at [X]%");
            templates.add("I followed the [pattern name] pattern for this implementation");
            templates.add("This change improves performance by [X]%");
        } else {
            // 通用模板
            templates.add("Could you clarify what you mean by [specific term]?");
            templates.add("I understand. Let me explain our approach...");
            templates.add("That's a valid concern. We can address it by...");
            templates.add("I agree with that suggestion. We should...");
            templates.add("Let me check with the team and get back to you");
        }
        
        return templates;
    }
}