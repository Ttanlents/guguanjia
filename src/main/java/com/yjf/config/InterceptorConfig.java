package com.yjf.config;

import com.yjf.Interceptor.LoginInterceptor;
import com.yjf.Interceptor.SysResourcesInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 余俊锋
 * @date 2020/11/3 15:31
 * @Description
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    SysResourcesInterceptor sysResourcesInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //需要拦截的路径，通常都是多个，所以使用数组形式   只要session中有登录信息  立马就会放行
        String[] addPathPatterns = {
                "/**"
        };
        //不需要的拦截路径，同上
        String[] excludePathPatterns = {
                "/notauth.html","/login.html","/notlogin.html","/static/**","/kaptcha/**","/user/**"
        };
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);

        String[] addPathPatterns2 = {
                "/**"
        };
        String[] excludePathPatterns2 = {
                "/notauth.html","/login.html","/notlogin.html","/static/**","/kaptcha/**","/user/**"
        };
        registry.addInterceptor(sysResourcesInterceptor).addPathPatterns(addPathPatterns2).excludePathPatterns(excludePathPatterns2);


    }
}
