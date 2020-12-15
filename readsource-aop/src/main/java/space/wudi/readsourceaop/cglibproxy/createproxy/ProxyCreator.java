package space.wudi.readsourceaop.cglibproxy.createproxy;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;

class ProxyCreator {
    private final static String SAVE_PATH = "/Users/wudi/program/javaworkspace/readsource/readsource-aop/target";
    static TargetObject createByNormalInterceptor(){
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, SAVE_PATH);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallbacks(new Callback[]{new MyMethodInterceptor("a"), new MyMethodInterceptor("b")});
        enhancer.setCallbackFilter(method -> method.getName().contains("say") ? 0 : 1);
        return (TargetObject)enhancer.create();
    }
}
