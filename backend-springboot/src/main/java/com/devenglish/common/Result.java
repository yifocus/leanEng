package com.devenglish.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "统一响应结果")
public class Result<T> implements Serializable {
    
    @Schema(description = "响应码")
    private Integer code;
    
    @Schema(description = "响应消息")
    private String message;
    
    @Schema(description = "响应数据")
    private T data;
    
    @Schema(description = "时间戳")
    private Long timestamp;
    
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }
    
    private Result(Integer code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public static <T> Result<T> success() {
        return new Result<>(200, "Success", null);
    }
    
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "Success", data);
    }
    
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }
    
    public static <T> Result<T> error() {
        return new Result<>(500, "Operation failed", null);
    }
    
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }
    
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}