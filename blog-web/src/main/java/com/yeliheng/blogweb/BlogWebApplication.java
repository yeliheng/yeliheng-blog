package com.yeliheng.blogweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.yeliheng.**.mapper")
@ComponentScan(basePackages = "com.yeliheng")
public class BlogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogWebApplication.class, args);
        System.out.println("\n" +
                "██╗   ██╗███████╗██╗     ██╗██╗  ██╗███████╗███╗   ██╗ ██████╗       ██████╗ ██╗      ██████╗  ██████╗ \n" +
                "╚██╗ ██╔╝██╔════╝██║     ██║██║  ██║██╔════╝████╗  ██║██╔════╝       ██╔══██╗██║     ██╔═══██╗██╔════╝ \n" +
                " ╚████╔╝ █████╗  ██║     ██║███████║█████╗  ██╔██╗ ██║██║  ███╗█████╗██████╔╝██║     ██║   ██║██║  ███╗\n" +
                "  ╚██╔╝  ██╔══╝  ██║     ██║██╔══██║██╔══╝  ██║╚██╗██║██║   ██║╚════╝██╔══██╗██║     ██║   ██║██║   ██║\n" +
                "   ██║   ███████╗███████╗██║██║  ██║███████╗██║ ╚████║╚██████╔╝      ██████╔╝███████╗╚██████╔╝╚██████╔╝\n" +
                "   ╚═╝   ╚══════╝╚══════╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝       ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ \n" +
                "                                             博客系统启动成功!                                  \n");
    }

}
