-- 删除已存在的表（如果存在）
DROP TABLE IF EXISTS user_quiz_history;
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS daily_tasks;
DROP TABLE IF EXISTS scenarios;
DROP TABLE IF EXISTS quiz_questions;
DROP TABLE IF EXISTS vocabulary;
DROP TABLE IF EXISTS users;

-- 初始化用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    nickname VARCHAR(50),
    avatar_url VARCHAR(200),
    english_level VARCHAR(20),
    learning_goal VARCHAR(200),
    level INT DEFAULT 1,
    experience INT DEFAULT 0,
    streak_days INT DEFAULT 0,
    total_learning_time INT DEFAULT 0,
    daily_target INT DEFAULT 30,
    enabled BOOLEAN DEFAULT TRUE,
    last_login_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

-- 初始化词汇表
CREATE TABLE IF NOT EXISTS vocabulary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    term VARCHAR(100) NOT NULL,
    definition TEXT,
    definition_cn TEXT,
    category VARCHAR(50),
    pronunciation VARCHAR(100),
    example_sentence TEXT,
    example_sentence_cn TEXT,
    translation VARCHAR(200),
    difficulty VARCHAR(20),
    tags VARCHAR(200),
    parent_id BIGINT DEFAULT NULL,
    is_parent BOOLEAN DEFAULT FALSE,
    learn_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES vocabulary(id) ON DELETE CASCADE
);

-- 初始化场景表
CREATE TABLE IF NOT EXISTS scenarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    avatar VARCHAR(50),
    role VARCHAR(100),
    difficulty VARCHAR(20),
    category VARCHAR(50),
    estimated_time INT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 初始化每日任务表
CREATE TABLE IF NOT EXISTS daily_tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    xp_reward INT DEFAULT 0,
    is_completed BOOLEAN DEFAULT FALSE,
    user_id BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 初始化成就表
CREATE TABLE IF NOT EXISTS achievements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    icon VARCHAR(50),
    xp_reward INT DEFAULT 0,
    requirement_type VARCHAR(50),
    requirement_value INT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 初始化测验题目表
CREATE TABLE IF NOT EXISTS quiz_questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question TEXT NOT NULL,
    type VARCHAR(50) DEFAULT 'GENERAL',
    options TEXT,
    correct_answer VARCHAR(200),
    category VARCHAR(50),
    difficulty VARCHAR(20),
    explanation TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 删除已存在的数据
DELETE FROM vocabulary;
DELETE FROM scenarios;
DELETE FROM quiz_questions;
DELETE FROM achievements;

-- 插入示例词汇数据（包含父子关系）
-- 父词汇
INSERT INTO vocabulary (term, definition, definition_cn, category, pronunciation, example_sentence, example_sentence_cn, translation, difficulty, tags, parent_id, is_parent) VALUES
-- 架构相关父词汇
('Software Architecture', 'The fundamental structures of a software system and the discipline of creating such structures', '软件系统的基本结构以及创建此类结构的学科', '架构', '/ˈsɒftweə ˈɑːkɪtektʃə/', 'Good software architecture is crucial for scalable applications.', '良好的软件架构对于可扩展的应用程序至关重要。', '软件架构', 'INTERMEDIATE', '架构,设计', NULL, true),
-- DevOps相关父词汇
('DevOps Practices', 'A set of practices that combines software development and IT operations', '结合软件开发和IT运维的一套实践方法', 'DevOps', '/devɒps ˈpræktɪsɪz/', 'DevOps practices help teams deliver software faster and more reliably.', 'DevOps实践帮助团队更快、更可靠地交付软件。', 'DevOps实践', 'INTERMEDIATE', 'DevOps,运维', NULL, true),
-- 测试相关父词汇
('Software Testing', 'The process of evaluating and verifying software functionality', '评估和验证软件功能的过程', '测试', '/ˈsɒftweə ˈtestɪŋ/', 'Software testing is essential for quality assurance.', '软件测试对于质量保证至关重要。', '软件测试', 'BEGINNER', '测试,质量', NULL, true),
-- 项目管理相关父词汇
('Agile Development', 'A methodology for software development that emphasizes flexibility and collaboration', '强调灵活性和协作的软件开发方法论', '项目管理', '/ˈædʒaɪl dɪˈveləpmənt/', 'Agile development helps teams respond to changing requirements.', '敏捷开发帮助团队响应不断变化的需求。', '敏捷开发', 'BEGINNER', '敏捷,管理', NULL, true);

-- 子词汇（架构相关）
INSERT INTO vocabulary (term, definition, definition_cn, category, pronunciation, example_sentence, example_sentence_cn, translation, difficulty, tags, parent_id, is_parent) VALUES
('Microservices', 'An architectural approach that decomposes a single application into a suite of small services', '将单个应用程序分解为一套小服务的架构方法', '架构', '/ˌmaɪkrəʊˈsɜːvɪsɪz/', 'We are migrating our monolithic application to a microservices architecture.', '我们正在将单体应用程序迁移到微服务架构。', '微服务', 'INTERMEDIATE', '架构,后端', 1, false),
('API Gateway', 'A server that acts as an API front-end, receives API requests, and distributes them', '充当API前端的服务器，接收API请求并分发它们', '架构', '/eɪ piː aɪ ˈɡeɪtweɪ/', 'The API gateway handles authentication before routing requests to microservices.', 'API网关在将请求路由到微服务之前处理身份验证。', 'API网关', 'INTERMEDIATE', '架构,API', 1, false),
('Load Balancer', 'A device that distributes network or application traffic across servers', '在服务器之间分配网络或应用程序流量的设备', '架构', '/ləʊd ˈbælənsə/', 'The load balancer distributes incoming requests across multiple servers.', '负载均衡器将传入请求分配到多个服务器上。', '负载均衡器', 'INTERMEDIATE', '架构,性能', 1, false);

-- 子词汇（DevOps相关）
INSERT INTO vocabulary (term, definition, definition_cn, category, pronunciation, example_sentence, example_sentence_cn, translation, difficulty, tags, parent_id, is_parent) VALUES
('CI/CD Pipeline', 'Continuous Integration and Continuous Deployment/Delivery practices', '持续集成和持续部署/交付实践', 'DevOps', '/siː aɪ siː diː ˈpaɪplaɪn/', 'Our CI/CD pipeline automatically tests and deploys code changes.', '我们的CI/CD管道自动测试和部署代码更改。', '持续集成/持续部署管道', 'INTERMEDIATE', 'DevOps,自动化', 2, false),
('Docker Container', 'A lightweight, standalone, executable package of software', '轻量级、独立的、可执行的软件包', 'DevOps', '/ˈdɒkə kənˈteɪnə/', 'We use Docker containers to ensure consistency across environments.', '我们使用Docker容器来确保跨环境的一致性。', 'Docker容器', 'INTERMEDIATE', 'DevOps,容器化', 2, false),
('Kubernetes', 'An open-source container orchestration platform', '开源容器编排平台', 'DevOps', '/kuːbəˈnetɪs/', 'Kubernetes manages our containerized applications in production.', 'Kubernetes在生产环境中管理我们的容器化应用程序。', 'Kubernetes', 'ADVANCED', 'DevOps,编排', 2, false);

-- 子词汇（测试相关）
INSERT INTO vocabulary (term, definition, definition_cn, category, pronunciation, example_sentence, example_sentence_cn, translation, difficulty, tags, parent_id, is_parent) VALUES
('Unit Testing', 'Testing individual units or components of a software', '测试软件的各个单元或组件', '测试', '/ˈjuːnɪt ˈtestɪŋ/', 'We write unit tests to ensure each function works correctly.', '我们编写单元测试来确保每个函数正确工作。', '单元测试', 'BEGINNER', '测试,质量', 3, false),
('Integration Testing', 'Testing the interfaces between components or systems', '测试组件或系统之间的接口', '测试', '/ˌɪntɪˈɡreɪʃən ˈtestɪŋ/', 'Integration testing verifies that different modules work together.', '集成测试验证不同模块是否能够协同工作。', '集成测试', 'INTERMEDIATE', '测试,集成', 3, false);

-- 子词汇（敏捷开发相关）
INSERT INTO vocabulary (term, definition, definition_cn, category, pronunciation, example_sentence, example_sentence_cn, translation, difficulty, tags, parent_id, is_parent) VALUES
('Scrum Master', 'A facilitator for an agile development team', '敏捷开发团队的促进者', '项目管理', '/skrʌm ˈmɑːstə/', 'Our Scrum Master helps remove impediments during the sprint.', '我们的Scrum主管帮助在冲刺期间消除障碍。', 'Scrum主管', 'BEGINNER', '敏捷,管理', 4, false),
('Sprint Planning', 'A meeting where the team plans work for the upcoming sprint', '团队为即将到来的冲刺规划工作的会议', '项目管理', '/sprɪnt ˈplænɪŋ/', 'During sprint planning, we estimate story points for each task.', '在冲刺规划期间，我们为每个任务估算故事点。', '冲刺规划', 'BEGINNER', '敏捷,规划', 4, false);

-- 独立词汇（无父子关系）
INSERT INTO vocabulary (term, definition, definition_cn, category, pronunciation, example_sentence, example_sentence_cn, translation, difficulty, tags, parent_id, is_parent) VALUES
('Dependency Injection', 'A design pattern for implementing IoC between classes and their dependencies', '在类及其依赖项之间实现控制反转的设计模式', '设计模式', '/dɪˈpendənsi ɪnˈdʒekʃən/', 'Spring framework uses dependency injection to manage object creation.', 'Spring框架使用依赖注入来管理对象创建。', '依赖注入', 'ADVANCED', '设计模式,编程', NULL, false),
('Pull Request', 'A method of submitting contributions to a software project', '向软件项目提交贡献的方法', '版本控制', '/pʊl rɪˈkwest/', 'Please review my pull request before merging to main branch.', '请在合并到主分支之前审查我的拉取请求。', '拉取请求', 'BEGINNER', 'Git,协作', NULL, false);

-- 插入示例场景数据
INSERT INTO scenarios (name, description, avatar, role, difficulty, category, estimated_time) VALUES
('Sprint Planning Meeting', 'Discuss and plan the upcoming sprint with your team', '👨‍💼', 'Product Manager', 'INTERMEDIATE', 'Scrum', 15),
('Code Review Session', 'Review and discuss code changes with a senior developer', '👩‍💻', 'Senior Developer', 'INTERMEDIATE', 'Development', 20),
('Client Requirements Discussion', 'Clarify project requirements with an international client', '🤵', 'Client Representative', 'ADVANCED', 'Business', 25),
('Daily Stand-up', 'Participate in a daily scrum meeting', '👥', 'Scrum Team', 'BEGINNER', 'Scrum', 10),
('Technical Interview', 'Practice for a technical interview at a Silicon Valley company', '👔', 'Technical Interviewer', 'ADVANCED', 'Interview', 30);

-- 初始化用户答题历史表
CREATE TABLE IF NOT EXISTS user_quiz_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    quiz_id BIGINT NOT NULL,
    user_answer VARCHAR(10),
    is_correct BOOLEAN NOT NULL,
    time_spent INT,
    xp_earned INT DEFAULT 0,
    answer_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    quiz_type VARCHAR(50),
    quiz_difficulty VARCHAR(20),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_quiz_id (quiz_id),
    INDEX idx_answer_time (answer_time)
);

-- 插入示例测验题目
INSERT INTO quiz_questions (question, type, options, correct_answer, category, difficulty, explanation) VALUES
('What does "refactor" mean in software development?', 'VOCABULARY', '["Add new features", "Restructure existing code without changing functionality", "Delete old code", "Write documentation"]', 'Restructure existing code without changing functionality', 'Development', 'BEGINNER', 'Refactoring improves code structure and readability without altering its external behavior.'),
('Which HTTP status code indicates "Not Found"?', 'TECHNICAL', '["200", "404", "500", "301"]', '404', 'Web Development', 'BEGINNER', 'HTTP 404 indicates that the requested resource could not be found on the server.'),
('What is the purpose of a REST API?', 'TECHNICAL', '["Database management", "Enable communication between different software systems", "Compile code", "Design user interfaces"]', 'Enable communication between different software systems', 'API', 'INTERMEDIATE', 'REST APIs provide a standardized way for different applications to communicate over HTTP.');

-- 插入示例成就数据
INSERT INTO achievements (name, description, icon, xp_reward, requirement_type, requirement_value) VALUES
('First Steps', 'Complete your first lesson', '🎯', 50, 'lessons_completed', 1),
('Week Warrior', 'Maintain a 7-day learning streak', '🔥', 200, 'streak_days', 7),
('Vocabulary Master', 'Learn 100 technical terms', '📚', 500, 'vocabulary_learned', 100),
('Conversation Expert', 'Complete 10 scenario conversations', '💬', 300, 'scenarios_completed', 10),
('Quiz Champion', 'Answer 50 quiz questions correctly', '🏆', 400, 'quiz_correct', 50);