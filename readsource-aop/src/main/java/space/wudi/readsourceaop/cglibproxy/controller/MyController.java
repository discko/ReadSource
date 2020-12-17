package space.wudi.readsourceaop.cglibproxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.wudi.readsourceaop.cglibproxy.bean.User;
import space.wudi.readsourceaop.cglibproxy.service.CgLibDynamicService;

@RestController
public class MyController {

    private final CgLibDynamicService cgLibDynamicService;// = new CgLibDynamicService();

    @Autowired
    public MyController(CgLibDynamicService cgLibDynamicService) {
        this.cgLibDynamicService = cgLibDynamicService;
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
    @GetMapping("/testCgLibDynamicProxy")
    public String testCgLibDynamicProxy(String id){
        System.out.println("in controller");
        return cgLibDynamicService.useCgLibDynamicProxy(id);
    }
    @GetMapping("/testCgLibDynamicProxyReturnThis")
    public String testJdkDynamicProxyReturnThis(){
        String s = cgLibDynamicService.getClass().toString();
        System.out.println("in controller, proxy="+ s);
        CgLibDynamicService rt = cgLibDynamicService.testReturnThis();
        System.out.println("in controller rt.class = "+ rt.getClass().toString());
        return rt.getClass().toString();
    }

}
