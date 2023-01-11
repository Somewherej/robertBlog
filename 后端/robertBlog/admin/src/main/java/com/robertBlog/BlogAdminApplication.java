package com.robertBlog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Somewherej
 * @Date 2022-11-22 15:51
 * @Description
 */
@SpringBootApplication
@MapperScan("com.robertBlog.mapper")
public class BlogAdminApplication {
    public static void main(String[] args) {

        SpringApplication.run(BlogAdminApplication.class, args);
    }
}