package com.yeliheng.blogsystem.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.yeliheng.blogsystem.dao"})
@EntityScan(basePackages = {"com.yeliheng.blogsystem.domain"})
public class JpaConfig {

}
