package space.wudi.readsourceaop.cglibproxy.service;

public class CgLibDynamicService {
//    @AspectJoinPoint
    public String useCgLibDynamicProxy(String id) {
        System.out.println("in service");
        return "id="+id;
    }

//    @AspectJoinPoint
    public CgLibDynamicService testReturnThis() {
        System.out.println("in target object, this.class = "+this.getClass().toString());
        return this;
    }

}
