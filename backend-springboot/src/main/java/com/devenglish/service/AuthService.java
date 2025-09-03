package com.devenglish.service;

import com.devenglish.dto.LoginRequest;
import com.devenglish.dto.RegisterRequest;
import com.devenglish.dto.UserInfoResponse;

import java.util.Map;

public interface AuthService {
    
    Map<String, Object> register(RegisterRequest request);
    
    Map<String, Object> login(LoginRequest request);
    
    void logout(String token);
    
    UserInfoResponse getUserInfo(String token);
    
    Map<String, Object> refreshToken(String refreshToken);
}