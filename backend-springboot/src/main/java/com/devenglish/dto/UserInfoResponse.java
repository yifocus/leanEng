package com.devenglish.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "用户信息响应")
public class UserInfoResponse {
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "昵称")
    private String nickname;
    
    @Schema(description = "头像URL")
    private String avatarUrl;
    
    @Schema(description = "英语水平")
    private String englishLevel;
    
    @Schema(description = "等级")
    private Integer level;
    
    @Schema(description = "经验值")
    private Integer experience;
    
    @Schema(description = "连续学习天数")
    private Integer streakDays;
    
    @Schema(description = "总学习时间")
    private Integer totalLearningTime;
}