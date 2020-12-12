package space.wudi.readsourceaop.myproxy.proxy;

import space.wudi.readsourceaop.bean.User;
import space.wudi.readsourceaop.myproxy.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyAroundUserService extends Proxy implements UserService {
    private InAroundProcessor processor;
    public ProxyAroundUserService(Object target, MyInvocationHandler h) {
        super(h);
        try {
            Method method = target.getClass().getDeclaredMethod("login", String.class);
            h.setMethod(method);
        } catch (NoSuchMethodException ignore) {
        }
        h.setProxy(this);
        this.processor = new InAroundProcessor(h);
    }

    @Override
    public User login(String username) throws Throwable {
        return (User)((MyInvocationHandler)h).around.adviseAround(processor, username);
    }

    public static class MyInvocationHandler implements InvocationHandler {
        Object target;
        AroundMethod around;
        BeforeMethod before;
        AfterAdvice after;
        Proxy proxy;
        Method method;
        public MyInvocationHandler(Object target, AroundMethod around, BeforeMethod before, AfterMethod after, AfterReturningMethod afterReturning, AfterThrowingMethod afterThrowing){
            this.target = target;
            this.around = around;
            this.before = before;
            this.after = new AfterAdvice(this, after, afterReturning, afterThrowing);
        }
        void setMethod(Method method){
            this.method = method;
        }
        void setProxy(Proxy proxy){
            this.proxy = proxy;
        }
        Object invoke(Object ... args) throws Throwable{
            return invoke(proxy, method, args);
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(target, args);
        }
    }

    public static class InAroundProcessor{
        MyInvocationHandler h;

        InAroundProcessor(MyInvocationHandler invocationHandler){
            this.h = invocationHandler;
        }

        public Object processInAround(Object...args) throws Throwable{
            h.before.adviseBefore();
            return h.after.processAfter(args);
        }
    }

    @FunctionalInterface
    public interface AroundMethod {
        Object adviseAround(InAroundProcessor processor, Object...args) throws Throwable;
    }
    @FunctionalInterface
    public interface BeforeMethod{
        void adviseBefore();
    }
    @FunctionalInterface
    public interface AfterMethod{
        void adviseAfter();
    }
    @FunctionalInterface
    public interface AfterReturningMethod{
        void adviseAfterReturning(Object rtVal);
    }

    @FunctionalInterface
    public interface AfterThrowingMethod{
        void adviseAfterThrowing(Throwable throwable) throws Throwable;
    }
    private static class AfterAdvice{
        AfterMethod after;
        AfterReturningMethod afterReturning;
        AfterThrowingMethod afterThrowing;
        MyInvocationHandler h;
        AfterAdvice(MyInvocationHandler invocationHandler, AfterMethod after, AfterReturningMethod afterReturning, AfterThrowingMethod afterThrowing){
            this.h = invocationHandler;
            this.after = after;
            this.afterReturning = afterReturning;
            this.afterThrowing = afterThrowing;
        }
        Object processAfter(Object...args) throws Throwable {
            try {
                Object rtVal = h.invoke(args);
                afterReturning.adviseAfterReturning(rtVal);
                return rtVal;
            }catch(Throwable throwable){
                afterThrowing.adviseAfterThrowing(throwable);
            }finally{
                after.adviseAfter();
            }
            return null;
        }
    }
}
