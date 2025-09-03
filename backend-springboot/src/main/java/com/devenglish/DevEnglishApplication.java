package com.devenglish;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
@MapperScan("com.devenglish.mapper")
public class DevEnglishApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DevEnglishApplication.class, args);
        System.out.println("========================================");
        System.out.println("   DevEnglish Pro Started Successfully!   ");
        System.out.println("   Swagger: http://localhost:8081/swagger-ui/");
        System.out.println("========================================");
    }
}