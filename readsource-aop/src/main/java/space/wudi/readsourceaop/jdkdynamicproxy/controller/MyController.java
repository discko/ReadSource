package space.wudi.readsourceaop.jdkdynamicproxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.wudi.readsourceaop.jdkdynamicproxy.bean.User;
import space.wudi.readsourceaop.jdkdynamicproxy.service.JdkDynamicService;

@RestController
public class MyController {

    private final JdkDynamicService jdkDynamicService;

    @Autowired
    public MyController(JdkDynamicService jdkDynamicService) {
        this.jdkDynamicService = jdkDynamicService;
    }

    @GetMapping("/greeting")
    public String getInfo(String fromInfo){
        return "Hi, "+fromInfo+"!";
    }
    @GetMapping("/userinfo")
    public User getUserInfo(String fromInfo){
        return new User(fromInfo, "email");
    }
    @GetMapping("/exception")
    public String throwException(){
        throw new RuntimeException("an error occurred in controller");
    }
    @GetMapping("/testJdkDynamicProxy")
    public String testJdkDynamicProxy(String id){
        System.out.println("in controller");
        return jdkDynamicService.useJdkDynamicProxy(id);
    }
    @GetMapping("/testJdkDynamicProxyReturnThis")
    public String testJdkDynamicProxyReturnThis(){
        String s = jdkDynamicService.getClass().toString();
        System.out.println("in controller, proxy="+ s);
        JdkDynamicService rt = jdkDynamicService.testReturnThis();
        System.out.println("in controller rt.class = "+ rt.getClass().toString());
        return rt.getClass().toString();
    }

}
