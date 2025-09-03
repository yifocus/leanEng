-- 场景表
CREATE TABLE IF NOT EXISTS `scenarios` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '场景名称',
  `description` TEXT COMMENT '场景描述',
  `avatar` VARCHAR(50) COMMENT '场景图标',
  `role` VARCHAR(100) COMMENT '角色名称',
  `difficulty` VARCHAR(20) COMMENT '难度等级',
  `category` VARCHAR(50) COMMENT '场景分类',
  `estimated_time` INT COMMENT '预计时长(分钟)',
  `context` TEXT COMMENT '场景背景',
  `objectives` TEXT COMMENT '学习目标',
  `key_phrases` TEXT COMMENT '关键短语(JSON)',
  `tips` TEXT COMMENT '提示信息(JSON)',
  `total_dialogues` INT DEFAULT 0 COMMENT '总对话数',
  `min_score` INT DEFAULT 60 COMMENT '最低通过分数',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX idx_category (`category`),
  INDEX idx_difficulty (`difficulty`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='场景表';

-- 场景对话记录表
CREATE TABLE IF NOT EXISTS `scenario_dialogues` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `scenario_id` BIGINT NOT NULL COMMENT '场景ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `session_id` VARCHAR(50) COMMENT '会话ID',
  `role` VARCHAR(20) NOT NULL COMMENT '角色(user/ai/system)',
  `message` TEXT NOT NULL COMMENT '消息内容',
  `expected_response` TEXT COMMENT '期望回复',
  `sequence_number` INT COMMENT '对话序号',
  `evaluation` TEXT COMMENT '评价内容',
  `score` INT COMMENT '得分',
  `feedback` TEXT COMMENT '反馈信息',
  `grammar_errors` TEXT COMMENT '语法错误(JSON)',
  `vocabulary_suggestions` TEXT COMMENT '词汇建议(JSON)',
  `pronunciation_notes` TEXT COMMENT '发音注释(JSON)',
  `response_time` BIGINT COMMENT '响应时间(毫秒)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX idx_scenario_user (`scenario_id`, `user_id`),
  INDEX idx_session (`session_id`),
  INDEX idx_create_time (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='场景对话记录表';

-- 场景练习记录表
CREATE TABLE IF NOT EXISTS `scenario_practice_records` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `scenario_id` BIGINT NOT NULL COMMENT '场景ID',
  `session_id` VARCHAR(50) NOT NULL COMMENT '会话ID',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME COMMENT '结束时间',
  `duration` INT COMMENT '持续时长(秒)',
  `total_messages` INT DEFAULT 0 COMMENT '总消息数',
  `score` INT COMMENT '最终得分',
  `status` VARCHAR(20) DEFAULT 'in_progress' COMMENT '状态(in_progress/completed/abandoned)',
  `feedback` TEXT COMMENT '整体反馈',
  `improvements` TEXT COMMENT '改进建议(JSON)',
  `vocabulary_learned` TEXT COMMENT '学习的词汇(JSON)',
  `mistakes_made` TEXT COMMENT '犯的错误(JSON)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX idx_user_scenario (`user_id`, `scenario_id`),
  INDEX idx_session (`session_id`),
  INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='场景练习记录表';

-- 场景统计表
CREATE TABLE IF NOT EXISTS `scenario_statistics` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `scenario_id` BIGINT NOT NULL COMMENT '场景ID',
  `practice_count` INT DEFAULT 0 COMMENT '练习次数',
  `total_time` INT DEFAULT 0 COMMENT '总练习时长(分钟)',
  `average_score` DECIMAL(5,2) DEFAULT 0 COMMENT '平均得分',
  `best_score` INT DEFAULT 0 COMMENT '最高得分',
  `last_practice_time` DATETIME COMMENT '最后练习时间',
  `completion_rate` DECIMAL(5,2) DEFAULT 0 COMMENT '完成率',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY uk_user_scenario (`user_id`, `scenario_id`),
  INDEX idx_last_practice (`last_practice_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='场景统计表';

-- 场景模板表
CREATE TABLE IF NOT EXISTS `scenario_templates` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `scenario_id` BIGINT NOT NULL COMMENT '场景ID',
  `template_type` VARCHAR(50) COMMENT '模板类型',
  `sequence` INT DEFAULT 0 COMMENT '序号',
  `speaker` VARCHAR(50) COMMENT '说话者',
  `message` TEXT COMMENT '消息内容',
  `possible_responses` TEXT COMMENT '可能的回复(JSON)',
  `hint` TEXT COMMENT '提示',
  `key_points` TEXT COMMENT '关键点(JSON)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX idx_scenario (`scenario_id`),
  INDEX idx_type (`template_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='场景模板表';

-- 插入示例场景数据
INSERT INTO `scenarios` (`name`, `description`, `avatar`, `role`, `difficulty`, `category`, `estimated_time`, `context`, `objectives`) VALUES
('Sprint Planning Meeting', 'Plan and estimate tasks for the upcoming sprint with your distributed team', '📋', 'Scrum Master / Product Owner', 'INTERMEDIATE', 'Agile/Scrum', 20, 
 'You are participating in a sprint planning meeting for a new e-commerce platform. The team needs to estimate user stories, identify dependencies, and commit to sprint goals.',
 'Learn to discuss sprint goals, estimate story points, identify blockers and dependencies'),

('Daily Stand-up Meeting', 'Report your progress and discuss blockers in a daily scrum meeting', '🎯', 'Development Team', 'BEGINNER', 'Agile/Scrum', 10,
 'You are in a daily standup meeting with your remote team. Share what you did yesterday, what you plan to do today, and any blockers you are facing.',
 'Practice concise status updates, learn standup vocabulary, communicate blockers effectively'),

('Code Review Session', 'Present your code changes and respond to feedback from senior developers', '👨‍💻', 'Senior Developer', 'INTERMEDIATE', 'Development', 25,
 'You have submitted a pull request for a new authentication module. The senior developer is reviewing your code and providing feedback.',
 'Learn to explain code decisions, accept feedback professionally, discuss technical improvements'),

('Client Requirements Discussion', 'Clarify project requirements and scope with an international client', '🤝', 'Client Representative', 'ADVANCED', 'Business', 30,
 'You are meeting with a client from a US-based company to discuss requirements for their new mobile application.',
 'Practice requirement gathering, scope clarification, timeline negotiation'),

('Technical Architecture Discussion', 'Discuss system design and architecture decisions with the tech lead', '🏗️', 'Technical Architect', 'ADVANCED', 'Architecture', 35,
 'You are discussing the architecture for a microservices-based system with your technical architect.',
 'Learn architecture vocabulary, discuss scalability, explain technical trade-offs'),

('Bug Triage Meeting', 'Prioritize and assign bugs reported by QA team', '🐛', 'QA Lead', 'INTERMEDIATE', 'Quality Assurance', 15,
 'The QA team has reported several bugs from the latest release. You need to triage them and decide on priorities.',
 'Practice bug prioritization, severity assessment, assignment discussions'),

('Release Planning', 'Plan the deployment strategy and discuss rollback procedures', '🚀', 'DevOps Engineer', 'ADVANCED', 'DevOps', 25,
 'You are planning the production release for the next version of your application with the DevOps team.',
 'Learn deployment vocabulary, discuss rollback strategies, coordinate release timing'),

('Remote Team Sync', 'Coordinate with offshore team members across different time zones', '🌏', 'Remote Team Lead', 'INTERMEDIATE', 'Team Collaboration', 20,
 'You are syncing with your offshore team in India to coordinate on the current project deliverables.',
 'Practice cross-cultural communication, time zone coordination, task delegation');