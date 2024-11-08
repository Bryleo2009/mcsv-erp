package com.mcsv.producto_service.Config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogClass {

    private static final Logger log = LoggerFactory.getLogger(LogClass.class);

    // Captura todos los métodos en el paquete Dao
    @Before("execution(* com.mcsv.producto_service.Dao.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.debug(className + "." + methodName);
    }
}
