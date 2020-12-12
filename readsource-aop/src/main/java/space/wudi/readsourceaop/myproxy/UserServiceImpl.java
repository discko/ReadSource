package space.wudi.readsourceaop.myproxy;

import space.wudi.readsourceaop.bean.User;

public class UserServiceImpl implements UserService{

    @Override
    public User login(String username) throws Throwable {
        System.out.println("now logging in...");
        if(username == null){
            throw new NullPointerException("username cannot be null");
        }
        return new User(username, "email");
    }
}
