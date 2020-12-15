package space.wudi.readsourceaop.jdkdynamicproxy.myproxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.wudi.readsourceaop.jdkdynamicproxy.bean.User;

@RestController
public class UseMyProxyAutowiredController {
    final private UserService userService;

    @Autowired
    public UseMyProxyAutowiredController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginAutowired")
    public User loginAutowired(String username) throws Throwable{
        return userService.login(username);
    }

}
