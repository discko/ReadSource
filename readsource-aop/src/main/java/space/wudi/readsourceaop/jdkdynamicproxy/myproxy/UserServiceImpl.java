package space.wudi.readsourceaop.jdkdynamicproxy.myproxy;

import space.wudi.readsourceaop.jdkdynamicproxy.bean.User;

public class UserServiceImpl implements UserService{

    @SuppressWarnings("all")
    @Override
    public User login(String username) throws Throwable {
        System.out.println("now logging in...");
        if(username == null){
            throw new NullPointerException("username cannot be null");
        }
        return new User(username, "email");
    }
}
