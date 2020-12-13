package space.wudi.readsourceaop.service;

import org.springframework.stereotype.Service;
import space.wudi.readsourceaop.annotation.AspectJoinPoint;

@Service
public class JdkDynamicServiceImpl implements JdkDynamicService {
    @Override
    @AspectJoinPoint
    public String useJdkDynamicProxy(String id) {
        System.out.println("in service");
        return "id="+id;
    }

    @Override
    @AspectJoinPoint
    public JdkDynamicService testReturnThis() {
        System.out.println("in target object, this.class = "+this.getClass().toString());
        return this;
    }
}
