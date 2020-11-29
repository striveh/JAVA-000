package com.example.demo.aop;

import com.example.demo.annotation.DS;
import com.example.demo.context.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class DynamicDataSourceAspect {
    @Pointcut("@annotation(com.example.demo.annotation.DS)")
    public void dataSourcePointCut(){

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String dsKey = getDSAnnotation(joinPoint).value();
        DynamicDataSourceContextHolder.setContextKey(dsKey);
        try{
            return joinPoint.proceed();
        }finally {
            DynamicDataSourceContextHolder.removeContextKey();
        }
    }

    /**
     * 根据类或方法获取数据源注解
     * @param joinPoint
     * @return
     */
    private DS getDSAnnotation(ProceedingJoinPoint joinPoint){
        Class<?> targetClass = joinPoint.getTarget().getClass();
        DS dsAnnotation = targetClass.getAnnotation(DS.class);
        // 先判断类的注解，再判断方法注解
        if(Objects.nonNull(dsAnnotation)){
            return dsAnnotation;
        }else{
            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            return methodSignature.getMethod().getAnnotation(DS.class);
        }
    }
}