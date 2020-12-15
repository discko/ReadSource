package space.wudi.readsourceaop.jdkdynamicproxy.myproxy;

import space.wudi.readsourceaop.jdkdynamicproxy.bean.User;

public interface UserService {
    User login(String username) throws Throwable;
}
