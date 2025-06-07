package com.user.center.config.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO hai 去掉会怎么样？静态文件会被限制吗？
 */
@Configuration
@EnableWebMvc
public class WebAppConfig implements WebMvcConfigurer {

    /**
     * Configuration for static resources access<br>
     * addResourceHandler(): should define the path that is exposed<br>
     * addResourceLocations(): should define the path that the resource is
     * placed<br>
     * 
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/META-INF/resources/static/css/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/");
        registry.addResourceHandler("/").addResourceLocations("classpath:/");
        registry.addResourceHandler("/csrf").addResourceLocations("classpath:/");
    }

}
