package com.specification.mvc.conf;

import com.specification.mvc.common.AutoInterceptor;
import com.specification.mvc.common.TokenArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zenggs
 * @Date 2022/12/8
 */
@Configuration
public class WebMvcConf implements WebMvcConfigurer {
    @Resource
    TokenArgumentResolver tokenArgumentResolver;
    @Resource
    AutoInterceptor autoInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(tokenArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autoInterceptor);
    }
}
