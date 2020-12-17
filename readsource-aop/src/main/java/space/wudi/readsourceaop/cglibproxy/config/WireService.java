package space.wudi.readsourceaop.cglibproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.wudi.readsourceaop.cglibproxy.service.CgLibDynamicService;

@Configuration
public class WireService {
    @Bean
    public CgLibDynamicService createCgLibDynamicService(){
        return new CgLibDynamicService();
    }
}
