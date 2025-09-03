package com.devenglish.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "登录请求")
public class LoginRequest {
    
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名或邮箱", required = true)
    private String username;
    
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    
    @ApiModelProperty(value = "记住我")
    private Boolean rememberMe = false;
}