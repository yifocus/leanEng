package com.devenglish.controller;

import com.devenglish.common.Result;
import com.devenglish.dto.LoginRequest;
import com.devenglish.dto.RegisterRequest;
import com.devenglish.dto.UserInfoResponse;
import com.devenglish.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(tags = "用户认证管理")
@CrossOrigin
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<Map<String, Object>> register(@Validated @RequestBody RegisterRequest request) {
        return Result.success(authService.register(request));
    }
    
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<Map<String, Object>> login(@Validated @RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }
    
    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public Result<Void> logout(HttpServletRequest request) {
        String token = extractToken(request);
        authService.logout(token);
        return Result.success();
    }
    
    @GetMapping("/user-info")
    @ApiOperation("获取用户信息")
    public Result<UserInfoResponse> getUserInfo(HttpServletRequest request) {
        String token = extractToken(request);
        return Result.success(authService.getUserInfo(token));
    }
    
    @PostMapping("/refresh")
    @ApiOperation("刷新Token")
    public Result<Map<String, Object>> refreshToken(@RequestParam String refreshToken) {
        return Result.success(authService.refreshToken(refreshToken));
    }
    
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}