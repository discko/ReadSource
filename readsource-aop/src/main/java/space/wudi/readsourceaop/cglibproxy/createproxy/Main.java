package space.wudi.readsourceaop.cglibproxy.createproxy;

public class Main {
    public static void main(String[] args) {
        TargetObject to = ProxyCreator.createByNormalInterceptor();
        to.sayHi("WuDi");
        String response = to.whoAreYou("stranger");
        System.out.println(response);
    }
}
