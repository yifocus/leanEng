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
    category VARCHAR(50),
    pronunciation VARCHAR(100),
    example_sentence TEXT,
    translation VARCHAR(200),
    difficulty VARCHAR(20),
    tags VARCHAR(200),
    learn_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    options TEXT,
    correct_answer VARCHAR(200),
    category VARCHAR(50),
    difficulty VARCHAR(20),
    explanation TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入示例词汇数据
INSERT INTO vocabulary (term, definition, category, pronunciation, example_sentence, translation, difficulty, tags) VALUES
('Microservices', 'An architectural style that structures an application as a collection of loosely coupled services', '架构', '/ˌmaɪkrəʊˈsɜːvɪsɪz/', 'We are migrating our monolithic application to a microservices architecture.', '微服务', 'INTERMEDIATE', 'architecture,backend'),
('CI/CD Pipeline', 'Continuous Integration and Continuous Deployment/Delivery practices', 'DevOps', '/siː aɪ siː diː ˈpaɪplaɪn/', 'Our CI/CD pipeline automatically tests and deploys code changes.', '持续集成/持续部署管道', 'INTERMEDIATE', 'devops,automation'),
('Dependency Injection', 'A design pattern for implementing IoC between classes and their dependencies', '设计模式', '/dɪˈpendənsi ɪnˈdʒekʃən/', 'Spring framework uses dependency injection to manage object creation.', '依赖注入', 'ADVANCED', 'design-pattern,programming'),
('API Gateway', 'A server that acts as an API front-end, receives API requests, and distributes them', '架构', '/eɪ piː aɪ ˈɡeɪtweɪ/', 'The API gateway handles authentication before routing requests to microservices.', 'API网关', 'INTERMEDIATE', 'architecture,api'),
('Scrum Master', 'A facilitator for an agile development team', '项目管理', '/skrʌm ˈmɑːstə/', 'Our Scrum Master helps remove impediments during the sprint.', 'Scrum主管', 'BEGINNER', 'agile,management'),
('Pull Request', 'A method of submitting contributions to a software project', '版本控制', '/pʊl rɪˈkwest/', 'Please review my pull request before merging to main branch.', '拉取请求', 'BEGINNER', 'git,collaboration'),
('Docker Container', 'A lightweight, standalone, executable package of software', 'DevOps', '/ˈdɒkə kənˈteɪnə/', 'We use Docker containers to ensure consistency across environments.', 'Docker容器', 'INTERMEDIATE', 'devops,containerization'),
('Load Balancer', 'A device that distributes network or application traffic across servers', '架构', '/ləʊd ˈbælənsə/', 'The load balancer distributes incoming requests across multiple servers.', '负载均衡器', 'INTERMEDIATE', 'architecture,performance'),
('Unit Testing', 'Testing individual units or components of a software', '测试', '/ˈjuːnɪt ˈtestɪŋ/', 'We write unit tests to ensure each function works correctly.', '单元测试', 'BEGINNER', 'testing,quality'),
('Kubernetes', 'An open-source container orchestration platform', 'DevOps', '/kuːbəˈnetɪs/', 'Kubernetes manages our containerized applications in production.', 'Kubernetes', 'ADVANCED', 'devops,orchestration');

-- 插入示例场景数据
INSERT INTO scenarios (name, description, avatar, role, difficulty, category, estimated_time) VALUES
('Sprint Planning Meeting', 'Discuss and plan the upcoming sprint with your team', '👨‍💼', 'Product Manager', 'INTERMEDIATE', 'Scrum', 15),
('Code Review Session', 'Review and discuss code changes with a senior developer', '👩‍💻', 'Senior Developer', 'INTERMEDIATE', 'Development', 20),
('Client Requirements Discussion', 'Clarify project requirements with an international client', '🤵', 'Client Representative', 'ADVANCED', 'Business', 25),
('Daily Stand-up', 'Participate in a daily scrum meeting', '👥', 'Scrum Team', 'BEGINNER', 'Scrum', 10),
('Technical Interview', 'Practice for a technical interview at a Silicon Valley company', '👔', 'Technical Interviewer', 'ADVANCED', 'Interview', 30);

-- 插入示例测验题目
INSERT INTO quiz_questions (question, options, correct_answer, category, difficulty, explanation) VALUES
('What does "refactor" mean in software development?', '["Add new features", "Restructure existing code without changing functionality", "Delete old code", "Write documentation"]', 'Restructure existing code without changing functionality', 'Development', 'BEGINNER', 'Refactoring improves code structure and readability without altering its external behavior.'),
('Which HTTP status code indicates "Not Found"?', '["200", "404", "500", "301"]', '404', 'Web Development', 'BEGINNER', 'HTTP 404 indicates that the requested resource could not be found on the server.'),
('What is the purpose of a REST API?', '["Database management", "Enable communication between different software systems", "Compile code", "Design user interfaces"]', 'Enable communication between different software systems', 'API', 'INTERMEDIATE', 'REST APIs provide a standardized way for different applications to communicate over HTTP.');

-- 插入示例成就数据
INSERT INTO achievements (name, description, icon, xp_reward, requirement_type, requirement_value) VALUES
('First Steps', 'Complete your first lesson', '🎯', 50, 'lessons_completed', 1),
('Week Warrior', 'Maintain a 7-day learning streak', '🔥', 200, 'streak_days', 7),
('Vocabulary Master', 'Learn 100 technical terms', '📚', 500, 'vocabulary_learned', 100),
('Conversation Expert', 'Complete 10 scenario conversations', '💬', 300, 'scenarios_completed', 10),
('Quiz Champion', 'Answer 50 quiz questions correctly', '🏆', 400, 'quiz_correct', 50);