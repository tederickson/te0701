package com.tools.rental.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * <a href="https://howtodoinjava.com/spring-boot/performance-logging-aspectj-aop">
 * Log the execution times of all public Controller methods without having to annotate each method.</a>
 */
@Slf4j
@Aspect
@Component
public class LogExecutionTime {
    // AOP expression for all public methods in a package
    @Around("execution(public * com.tools.rental.controller..*(..))")
    public Object profileControllerMethods(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Signature methodSignature = proceedingJoinPoint.getSignature();

        final String className = methodSignature.getDeclaringType().getSimpleName();
        final String methodName = methodSignature.getName();

        log.info("Start {}.{}", className, methodName);

        final StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        log.info("Execution time of {}.{} | {} ms", className, methodName, stopWatch.getTotalTimeMillis());

        return result;
    }
}

