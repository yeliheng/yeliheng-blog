package com.yeliheng.blogsystem.config;

import com.yeliheng.blogcommon.config.LocalStorageConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    /**
     * 静态资源处理器
     * @param registry 注册示例
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //头像静态资源
        registry.addResourceHandler("/**").addResourceLocations("file:///" + LocalStorageConfig.getFilePath() + "/avatar");
    }
}
