package space.wudi.readsourceaop.myproxy.proxy;

import space.wudi.readsourceaop.bean.User;
import space.wudi.readsourceaop.myproxy.UserService;

import java.util.Arrays;

public class ProxyUserService implements UserService {
    private UserService targetObject;
    public ProxyUserService(UserService targetObject){
        this.targetObject = targetObject;
    }
    @Override
    public User login(String username) throws Throwable {
        before(username);
        try{
            User rtVal = targetObject.login(username);
            afterReturning(rtVal, username);
            return rtVal;
        }catch(Throwable throwable){
            afterThrow(throwable, username);
        }finally {
            after(username);
        }
        return null;
    }
    private void after(Object...args){
        System.out.println("in After with args: "+Arrays.toString(args));
    }

    private void afterReturning(Object rtVal, Object...args){
        System.out.println("in AfterReturning with args: " + Arrays.toString(args)+" and rtVal: "+rtVal);
        ((User)rtVal).setEmail("modified by ProxyUserService");
    }

    private void afterThrow(Throwable throwable, String...args) throws Throwable{
        System.out.println("in AfterThrowing with args: " + Arrays.toString(args)+" and exception: "+throwable);
        throw new RuntimeException("meet a exception: "+ throwable.getMessage());
    }

    private void before(Object...args){
        System.out.println("in Before with args: "+ Arrays.toString(args));
    }

}
