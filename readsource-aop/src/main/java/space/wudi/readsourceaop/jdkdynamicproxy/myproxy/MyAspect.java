package space.wudi.readsourceaop.jdkdynamicproxy.myproxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.wudi.readsourceaop.jdkdynamicproxy.myproxy.proxy.ProxyAroundUserService;

@Configuration
public class MyAspect {
    @Bean
    public UserService getUserServiceProxy(){
        UserService targetObject = new UserServiceImpl();
        ProxyAroundUserService.MyInvocationHandler h = new ProxyAroundUserService.MyInvocationHandler(
                targetObject,
                // around
                (processor, args)->{
                    System.out.println("in Around before processInAround");
                    Object rtVal = processor.processInAround(args);
                    System.out.println("in Around after processInAround");
                    return rtVal;
                },
                // before
                ()-> System.out.println("in Before"),
                // after
                ()-> System.out.println("in After"),
                // after returning
                (rtVal)-> System.out.println("in AfterReturning with rtVal: "+rtVal),
                // after throwing
                (throwable) -> {
                    System.out.println("in AfterThrowing with exception: "+throwable);
                    throw throwable;
                }
        );
        return new ProxyAroundUserService(targetObject, h);
    }
}
