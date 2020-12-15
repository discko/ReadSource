package space.wudi.readsourceaop.cglibproxy.codemyself;

class Proxy extends Target {
    @Override
    void method(){
        System.out.println("before target method");
        super.method();
        System.out.println("after target method");
    }
}
