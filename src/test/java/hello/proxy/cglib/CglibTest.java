package hello.proxy.cglib;

import hello.common.service.ConcreteService;
import hello.proxy.cglib.code.TimeMethodInterceptor;
import java.lang.reflect.Proxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreteService service = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(service));
        ConcreteService proxy = (ConcreteService) enhancer.create();

        log.info("targetClass = {}", service.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.call();
    }
}
