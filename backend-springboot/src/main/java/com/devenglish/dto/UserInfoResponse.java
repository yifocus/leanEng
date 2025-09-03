package com.devenglish.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "用户信息响应")
public class UserInfoResponse {
    
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    
    @ApiModelProperty(value = "用户名")
    private String username;
    
    @ApiModelProperty(value = "邮箱")
    private String email;
    
    @ApiModelProperty(value = "昵称")
    private String nickname;
    
    @ApiModelProperty(value = "头像URL")
    private String avatarUrl;
    
    @ApiModelProperty(value = "英语水平")
    private String englishLevel;
    
    @ApiModelProperty(value = "等级")
    private Integer level;
    
    @ApiModelProperty(value = "经验值")
    private Integer experience;
    
    @ApiModelProperty(value = "连续学习天数")
    private Integer streakDays;
    
    @ApiModelProperty(value = "总学习时间")
    private Integer totalLearningTime;
}