package space.wudi.readsourceaop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.wudi.readsourceaop.bean.User;

@RestController
public class MyController {
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
}
