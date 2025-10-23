package poly.edu.lab8.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import poly.edu.lab8.interceptor.AuthInterceptor;
import poly.edu.lab8.interceptor.LogInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor auth;

    @Autowired
    LogInterceptor log;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(log)
                .addPathPatterns("/**");

        registry.addInterceptor(auth)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/home");
    }
}
