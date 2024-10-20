package com.ainetwork.config;


import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口
                .allowCredentials(true) // 是否发送 Cookie
                .allowedOriginPatterns("*") // 支持域
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 支持方法
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 指定一条 match 规则
            SaRouter
//                    .match("/**")    // 拦截的 path 列表，可以写多个 */
                    .notMatch("/**")
                    .notMatch("/sys/**")//系统服务全排除
                    // 下边的是knife4j使用的
                    .notMatch("/*.html")
                    .notMatch("/swagger-resources")
                    .notMatch("/webjars/**")
                    .notMatch("/**/api-docs")
                    .notMatch("/dist/**")
                    .notMatch("/js/**")
                    .notMatch("/css/**")
                    .notMatch("/dist/**")
                    .notMatch("/img/**")
//                    .notMatch("/")
//                    .notMatch("/oidXml/*") // 这是用于xml测试的
                    .notMatch("")
                    .notMatch("/favicon.ico")
                    .notMatch("/user/login", "/user/register") // 排除掉的 path 列表，可以写多个
                    .check(r -> StpUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式

        }))
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register");
        ;
    }
}
