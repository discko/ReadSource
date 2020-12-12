package space.wudi.readsourceaop.myproxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.wudi.readsourceaop.bean.User;
import space.wudi.readsourceaop.myproxy.proxy.ProxyUserService;

import javax.annotation.PostConstruct;

@RestController
public class UseMyProxyController {

    private UserService userService;

    private UserService userServiceProxy;

    @PostConstruct
    void postConstruct(){
        userService = new UserServiceImpl();
        userServiceProxy = new ProxyUserService(userService);
    }

    @GetMapping("/loginWithoutProxy")
    public User loginWithoutProxy(String username) throws Throwable{
        return userService.login(username);
    }

    @GetMapping("/loginWithProxy")
    public User UserLoginWithProxy(String username) throws Throwable{
        return userServiceProxy.login(username);
    }

}
