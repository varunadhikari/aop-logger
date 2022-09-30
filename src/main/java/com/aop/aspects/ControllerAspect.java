package com.aop.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class ControllerAspect {
    Logger logger = LogManager.getLogger(this.getClass());

    @Pointcut("execution(* com.aop.controller.*.*(..))" +
            " || execution(* com.product.controller.*.*(..))")
    public void controller() {
        //Advice
    }

    @Around("execution(* com.aop.controller.*.*(..))" +
            " || execution(* com.product.controller.*.*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        logger.info(" Entering into method {} of class {}", joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName());
        long startTime = System.currentTimeMillis();
        proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logger.info(" Exiting from method {} of class {}", joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName());
        logger.info(" Time taken by method {} in milliSeconds : {}", joinPoint.getSignature().getName(),
                duration);
        return proceed;
    }

    @AfterThrowing(pointcut = "execution(* com.aop.controller.*.*(..))" +
            " || execution(* com.product.controller.*.*(..))" , throwing = "exception")
    public void logAfter(JoinPoint joinPoint, Throwable exception){
        logger.info("Error Occurred on method {} : {}", joinPoint.getSignature().getName() , exception.getMessage());
    }

}
