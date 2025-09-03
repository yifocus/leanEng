package com.devenglish.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devenglish.common.exception.BusinessException;
import com.devenglish.dto.LoginRequest;
import com.devenglish.dto.RegisterRequest;
import com.devenglish.dto.UserInfoResponse;
import com.devenglish.entity.User;
import com.devenglish.mapper.UserMapper;
import com.devenglish.service.AuthService;
import com.devenglish.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    // private final StringRedisTemplate redisTemplate;
    
    @Override
    @Transactional
    public Map<String, Object> register(RegisterRequest request) {
        // 检查用户名是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        if (userMapper.selectOne(wrapper) != null) {
            throw new BusinessException("用户名已存在");
        }
        
        // 检查邮箱是否存在
        wrapper.clear();
        wrapper.eq(User::getEmail, request.getEmail());
        if (userMapper.selectOne(wrapper) != null) {
            throw new BusinessException("邮箱已被注册");
        }
        
        // 创建新用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname() != null ? request.getNickname() : request.getUsername());
        user.setEnglishLevel(request.getEnglishLevel());
        user.setLevel(1);
        user.setExperience(0);
        user.setStreakDays(0);
        user.setTotalLearningTime(0);
        user.setDailyTarget(30);
        user.setEnabled(true);
        user.setAvatarUrl("https://api.dicebear.com/7.x/avataaars/svg?seed=" + request.getUsername());
        
        userMapper.insert(user);
        
        // 生成token
        String token = jwtUtil.generateToken(user.getUsername());
        String refreshToken = RandomUtil.randomString(32);
        
        // 存储到Redis (暂时注释)
        // redisTemplate.opsForValue().set("token:" + user.getId(), token, 24, TimeUnit.HOURS);
        // redisTemplate.opsForValue().set("refresh:" + user.getId(), refreshToken, 7, TimeUnit.DAYS);
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("refreshToken", refreshToken);
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        result.put("nickname", user.getNickname());
        result.put("level", user.getLevel());
        result.put("experience", user.getExperience());
        
        return result;
    }
    
    @Override
    public Map<String, Object> login(LoginRequest request) {
        // 查找用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername())
                .or()
                .eq(User::getEmail, request.getUsername());
        
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        
        // 验证密码
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        
        // 检查账号是否启用
        if (!user.getEnabled()) {
            throw new BusinessException("账号已被禁用");
        }
        
        // 更新最后登录时间
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);
        
        // 生成token
        String token = jwtUtil.generateToken(user.getUsername());
        String refreshToken = RandomUtil.randomString(32);
        
        // 存储到Redis (暂时注释)
        // redisTemplate.opsForValue().set("token:" + user.getId(), token, 24, TimeUnit.HOURS);
        // redisTemplate.opsForValue().set("refresh:" + user.getId(), refreshToken, 7, TimeUnit.DAYS);
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("refreshToken", refreshToken);
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        result.put("nickname", user.getNickname());
        result.put("level", user.getLevel());
        result.put("experience", user.getExperience());
        result.put("streakDays", user.getStreakDays());
        
        return result;
    }
    
    @Override
    public void logout(String token) {
        if (token != null) {
            String username = jwtUtil.getUsernameFromToken(token);
            if (username != null) {
                User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
                if (user != null) {
                    // redisTemplate.delete("token:" + user.getId());
                    // redisTemplate.delete("refresh:" + user.getId());
                }
            }
        }
    }
    
    @Override
    public UserInfoResponse getUserInfo(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        return UserInfoResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .avatarUrl(user.getAvatarUrl())
                .englishLevel(user.getEnglishLevel())
                .level(user.getLevel())
                .experience(user.getExperience())
                .streakDays(user.getStreakDays())
                .totalLearningTime(user.getTotalLearningTime())
                .build();
    }
    
    @Override
    public Map<String, Object> refreshToken(String refreshToken) {
        // 实现刷新token逻辑
        // 这里简化处理，实际应该验证refreshToken
        Map<String, Object> result = new HashMap<>();
        result.put("token", jwtUtil.generateToken("user"));
        result.put("refreshToken", RandomUtil.randomString(32));
        return result;
    }
}