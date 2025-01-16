package kr.hhplus.be.server.config;

import kr.hhplus.be.server.config.interceptors.PerformanceInterceptor;
import kr.hhplus.be.server.config.interceptors.UserExistenceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserExistenceInterceptor userExistenceInterceptor;

    public WebConfig(UserExistenceInterceptor userExistenceInterceptor) {
        this.userExistenceInterceptor = userExistenceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PerformanceInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns();

        registry.addInterceptor(userExistenceInterceptor)
                .addPathPatterns("/**");
    }


}
