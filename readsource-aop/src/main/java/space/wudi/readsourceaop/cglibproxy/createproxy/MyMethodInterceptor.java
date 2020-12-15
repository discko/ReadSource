package space.wudi.readsourceaop.cglibproxy.createproxy;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;
public class MyMethodInterceptor implements MethodInterceptor {
    private final String id;
    MyMethodInterceptor(String id){
        this.id = id;
    }
    /**
     * @param sub enhanced object
     * @param method method in target object
     * @param args method arguments
     * @param methodProxy proxy's method
     * @return origin or modified return value
     * @throws Throwable maybe something unexpected happened
     */
    @Override
    public Object intercept(Object sub, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before from "+id);
        Object rtVal = methodProxy.invokeSuper(sub, args);
        System.out.println("after from "+id+" with rtVal: "+rtVal);
        return rtVal;
    }
}
