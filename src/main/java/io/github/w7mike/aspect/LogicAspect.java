package io.github.w7mike.aspect;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogicAspect {

    private final Timer projectCreateGroupTimer;

    public LogicAspect(final MeterRegistry registry) {
        projectCreateGroupTimer = registry.timer("logic.project.create.group");
    }

    @Around("execution(* io.github.w7mike.logic.ProjectsService.createGroup(..))")
    Object aroundProjectCreateGroup(ProceedingJoinPoint joinPoint) {
        return projectCreateGroupTimer.record(()->{
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                if (e instanceof RuntimeException){
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        });
    }
}
