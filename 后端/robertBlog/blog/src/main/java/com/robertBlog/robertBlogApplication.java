package com.robertBlog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 * @author Somewherej
 * @data 2022-10-25 15:33
 * @description
 */
// 开始定时任务功能
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.robertBlog.service"})
@MapperScan("com.robertBlog.mapper")
public class robertBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(robertBlogApplication.class,args);
    }
}