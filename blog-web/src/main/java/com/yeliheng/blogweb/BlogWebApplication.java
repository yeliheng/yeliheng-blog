package com.yeliheng.blogweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.yeliheng.**.mapper")
@ComponentScan(basePackages = "com.yeliheng")
public class BlogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogWebApplication.class, args);
    }

}
