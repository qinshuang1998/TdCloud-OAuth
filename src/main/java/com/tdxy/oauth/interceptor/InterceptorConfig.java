package com.tdxy.oauth.interceptor;

import com.tdxy.oauth.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录态拦截器配置
 *
 * @author Qug_
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public ApiHeaderInterceptor apiHeaderInterceptor() {
        return new ApiHeaderInterceptor();
    }

    @Bean
    public ApiParamInterceptor apiParamInterceptor() {
        return new ApiParamInterceptor();
    }

    @Bean
    public ApiEndPointInterceptor apiEndPointInterceptor() {
        return new ApiEndPointInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessInterceptor())
                .addPathPatterns("/authorize");
        registry.addInterceptor(new ApiGlobalInterceptor())
                .addPathPatterns("/api/*").order(0);
        registry.addInterceptor(apiHeaderInterceptor())
                .addPathPatterns("/api/*").order(1);
        registry.addInterceptor(apiParamInterceptor())
                .addPathPatterns("/api/*").order(2);
        registry.addInterceptor(apiEndPointInterceptor())
                .addPathPatterns("/api/*").order(10);
    }
}
