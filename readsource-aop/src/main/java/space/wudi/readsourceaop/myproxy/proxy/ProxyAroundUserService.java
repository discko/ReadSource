package space.wudi.readsourceaop.myproxy.proxy;

import space.wudi.readsourceaop.bean.User;
import space.wudi.readsourceaop.myproxy.UserService;

import java.lang.reflect.Method;

public class ProxyAroundUserService implements UserService {


    private AspectProcessor processor;

    public ProxyAroundUserService(UserService targetObject, AroundMethod around, BeforeMethod before, AfterMethod after, AfterReturningMethod afterReturning, AfterThrowingMethod afterThrowing){
        Method method = null;
        try {
            method = targetObject.getClass().getDeclaredMethod("login", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        this.processor = new AspectProcessor(around, before, after, afterReturning, afterThrowing, method, targetObject);
    }

    @Override
    public User login(String username) throws Throwable {
        return (User)processor.processAspect(username);
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

    @FunctionalInterface
    public interface AroundMethod{
        Object adviseAround(InAroundProcessor processor, Object...args) throws Throwable;
    }

    private class AfterAdvice{
        Object targetObject;
        AfterMethod after;
        AfterReturningMethod afterReturning;
        AfterThrowingMethod afterThrowing;
        Method method;
        AfterAdvice(Object targetObject, AfterMethod after, Method method, AfterReturningMethod afterReturning, AfterThrowingMethod afterThrowing){
            this.targetObject = targetObject;
            this.after = after;
            this.method = method;
            this.afterReturning = afterReturning;
            this.afterThrowing = afterThrowing;
        }
        Object processAfter(Object...args) throws Throwable {
            try {
                Object rtVal = method.invoke(targetObject, args);
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

    public class InAroundProcessor{
        AfterAdvice after;
        BeforeMethod before;

        InAroundProcessor(BeforeMethod before, AfterAdvice after){
            this.before = before;
            this.after = after;
        }

        public Object processInAround(Object...args) throws Throwable{
            before.adviseBefore();
            return after.processAfter(args);
        }
    }

    private class AspectProcessor{
        AroundMethod around;
        InAroundProcessor processor;
        AspectProcessor(AroundMethod around, BeforeMethod before, AfterMethod after, AfterReturningMethod afterReturning, AfterThrowingMethod afterThrowing, Method method, Object targetObject){
            this.around = around;
            this.processor = new InAroundProcessor(before, new AfterAdvice(targetObject, after, method, afterReturning, afterThrowing));
        }
        Object processAspect(Object...args) throws Throwable{
            return around.adviseAround(processor, args);
        }
    }
}
