package hello.proxy.advisor;

import hello.common.service.ServiceImpl;
import hello.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Slf4j
public class MultiAdvisorTest {

    @Test
    @DisplayName("복수개 프록시")
    void multiAdvisorTest1() {
        // given
        ServiceInterface service = new ServiceImpl();

        ProxyFactory proxyFactory1 = new ProxyFactory(service);
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        proxyFactory1.addAdvisor(advisor1);
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory2.addAdvisor(advisor2);
        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy(); //실행

        // when
        proxy2.save();

        // then
    }

    @Test
    @DisplayName("단건 프록시에 복수개의 어드바이저 적용")
    void multiAdvisorTest2() {
        // given
        ServiceInterface service = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(service);
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2()));
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1()));

        ServiceInterface proxyService = (ServiceInterface) proxyFactory.getProxy(); //실행

        // when
        proxyService.save();

        // then
    }

    @Slf4j
    static class Advice1 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("called Advice1");
            return invocation.proceed();
        }
    }

    @Slf4j
    static class Advice2 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("called Advice2");
            return invocation.proceed();
        }
    }
}
