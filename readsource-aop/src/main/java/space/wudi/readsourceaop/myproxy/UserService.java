package space.wudi.readsourceaop.myproxy;

import space.wudi.readsourceaop.bean.User;

public interface UserService {
    User login(String username) throws Throwable;
}
