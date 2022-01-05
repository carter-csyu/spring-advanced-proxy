package hello.proxy.proxyfactory;

import static org.assertj.core.api.Assertions.assertThat;

import hello.common.advice.TimeAdvice;
import hello.common.service.ConcreteService;
import hello.common.service.ServiceImpl;
import hello.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {
        // given
        ServiceInterface service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxyService = (ServiceInterface) proxyFactory.getProxy();
        log.info("origin class: {}", service.getClass());
        log.info("proxy class: {}", proxyService.getClass());

        // when
        proxyService.find();
        proxyService.save();

        // then
        assertThat(AopUtils.isAopProxy(proxyService)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxyService)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxyService)).isFalse();
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy() {
        // given
        ConcreteService service = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        proxyFactory.addAdvice(new TimeAdvice());

        ConcreteService proxyService = (ConcreteService) proxyFactory.getProxy();
        log.info("origin class: {}", service.getClass());
        log.info("proxy class: {}", proxyService.getClass());

        // when
        proxyService.call();

        // then
        assertThat(AopUtils.isAopProxy(proxyService)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxyService)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxyService)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하여 강제로 CGLIB를 사용하도록 설정 가능")
    void proxyTargetClass() {
        // given
        ServiceInterface service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxyService = (ServiceInterface) proxyFactory.getProxy();
        log.info("origin class: {}", service.getClass());
        log.info("proxy class: {}", proxyService.getClass());

        // when
        proxyService.find();
        proxyService.save();

        // then
        assertThat(AopUtils.isAopProxy(proxyService)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxyService)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxyService)).isTrue();
    }
}
