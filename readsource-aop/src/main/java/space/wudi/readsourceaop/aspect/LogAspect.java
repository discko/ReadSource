package space.wudi.readsourceaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import space.wudi.readsourceaop.bean.User;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class LogAspect {

    private static AtomicInteger i = new AtomicInteger(1);

    //配置切入点，所有满足条件的函数都会被加入监控
    @Pointcut("execution(* space.wudi.readsourceaop.controller..*.*(..))")
    public void log(){}

    //配置Before通知
    @Before(value="log()")
    public void logBeforeController(JoinPoint joinPoint){
        System.out.printf("%d---before %s, args: %s\n", i.getAndIncrement(), joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
    }

    //配置Around通知
    @Around("log()")
    public Object logAroundController(ProceedingJoinPoint joinPoint) {
        System.out.printf("%d---around before %s, args: %s\n", i.getAndIncrement(), joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
        Object rtVal;
        try{
            rtVal = joinPoint.proceed();
        }catch(Throwable throwable){
            String str = throwable.toString();
            System.out.printf("%d---around after %s, catch: %s\n", i.getAndIncrement(), joinPoint.getSignature(), str);
            rtVal = "error happen because: " + str;

        }
        System.out.printf("%d---around after %s, returns: %s\n", i.getAndIncrement(), joinPoint.getSignature(), rtVal==null?"null":rtVal.toString());
        return rtVal;
    }
    //配置AfterReturning通知
    @AfterReturning(pointcut = "log()", returning = "rtVal")
    public void logAfterReturningController(JoinPoint joinPoint, Object rtVal){
        System.out.printf("%d---after returning %s, args: %s, rtVal: %s[%s]\n", i.getAndIncrement(), joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()), rtVal.toString(), rtVal.getClass());
        //修改return的值
        if(rtVal instanceof String){
            rtVal = rtVal + " modified by aspect";
        }
        if(rtVal instanceof User){
            ((User)rtVal).setEmail("modified by aspect");
        }
    }
    //配置After通知
    @After("log()")
    public void logAfterController(JoinPoint joinPoint){
        System.out.printf("%d---after %s, args: %s\n", i.getAndIncrement(), joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
    }
    //配置AfterThrowing通知
    @AfterThrowing(pointcut = "log()", throwing = "exception")
    public void logAfterThrowingController(JoinPoint joinPoint, Exception exception){
        System.out.printf("%d---after throwing %s, args: %s, exception: %s\n", i.getAndIncrement(), joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()), exception.toString());
    }

}
