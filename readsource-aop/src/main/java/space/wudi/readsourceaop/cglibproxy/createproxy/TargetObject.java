package space.wudi.readsourceaop.cglibproxy.createproxy;

public class TargetObject {
    public TargetObject(){

    }
    void sayHi(String to){
        System.out.println("Hi, "+to+"!");
    }
    String whoAreYou(String from){
        return String.format("Hi %s, it's WuDi", from);
    }
}
