package com.ainetwork.controller.aop;

import com.ainetwork.entity.User;
import com.ainetwork.service.UserService;
import com.ainetwork.util.JWTUtil;
import com.ainetwork.util.Result;
import com.ainetwork.util.ResultCode;
import com.ainetwork.util.Verify;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.log4j.Log4j2;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Log4j2
public class VerifyAOP {

    @Resource
    UserService userService;

    @Pointcut(value = "@annotation(com.ainetwork.util.Verify)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature  = (MethodSignature) joinPoint.getSignature();

        Verify verify = methodSignature.getMethod().getAnnotation(Verify.class);

        if(!verify.required()){
            return joinPoint.proceed();
        }

        // 处理请求
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if(sra == null) return Result.error(ResultCode.ERROR, "获取请求失败");

        HttpServletRequest request = sra.getRequest();

        String token = request.getHeader("token");
        if(StringUtils.isBlank(token)) return Result.error(ResultCode.NOT_LOGIN);

        DecodedJWT decodeToken;

        try{
            decodeToken = JWTUtil.getToken(token);
        }catch (Exception e){
            return Result.error(ResultCode.NOT_LOGIN);
        }
        Claim claim = decodeToken.getClaim("id");
        if(claim == null) return Result.error(ResultCode.NOT_LOGIN);
        Integer userId = claim.as(Integer.class);

        User user = userService.getById(userId);
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if(parameterNames.length == 0 || args.length == 0) return Result.error(ResultCode.ERROR, "获取userId参数失败");
        for(int i=0;i<parameterNames.length;i++){
            if("userId".equals(parameterNames[i]) || "id".equals(parameterNames[i])){
                args[i] = userId;
                break;
            }
        }
        return joinPoint.proceed(args);
    }

}
