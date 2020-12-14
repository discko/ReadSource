package space.wudi.readsourceaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {
    // if need to save the class file of the proxy
    // set VM option:
    // -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
    @Pointcut("@annotation(space.wudi.readsourceaop.annotation.AspectJoinPoint)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around before");
        Object rtVal = joinPoint.proceed();
        System.out.println("around after");
        return rtVal;
    }


    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint){
        System.out.println("before");
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint){
        System.out.println("after");
    }

    @AfterReturning(pointcut = "pointcut()", returning = "rtVal")
    public Object afterReturning(JoinPoint joinPoint, Object rtVal){
        System.out.println("after returning "+rtVal);
        return rtVal;
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) throws Throwable{
        System.out.println("after throwing "+throwable);
        throw throwable;
    }


}
