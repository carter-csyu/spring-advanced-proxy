package hello.proxy.advisor;

import hello.common.advice.TimeAdvice;
import hello.common.service.ServiceImpl;
import hello.common.service.ServiceInterface;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest1() {
        // given
        ServiceInterface service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice()));
        ServiceInterface proxyService = (ServiceInterface) proxyFactory.getProxy();

        // when
        proxyService.save();
        proxyService.find();

        // then
    }

    @Test
    @DisplayName("커스텀 포인트컷")
    void advisorTest2() {
        // given
        ServiceInterface service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new CustomPointcut(), new TimeAdvice()));
        ServiceInterface proxyService = (ServiceInterface) proxyFactory.getProxy();

        // when
        proxyService.save();
        proxyService.find();

        // then
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3() {
        // given
        ServiceInterface service = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(service);

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("save");
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(pointcut, new TimeAdvice()));
        ServiceInterface proxyService = (ServiceInterface) proxyFactory.getProxy();

        // when
        proxyService.save();
        proxyService.find();

        // then
    }

    static class CustomPointcut implements Pointcut {

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new CustomMatcher();
        }
    }

    static class CustomMatcher implements MethodMatcher {

        private static final String MATCH_NAME = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = MATCH_NAME.equals(method.getName());
            log.info("포인트컷 호출 method = {} targetClass = {}", method.getName(), targetClass);
            log.info("포인트컷 결과 result = {}", result);

            return result;
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            throw new UnsupportedOperationException();
        }
    }
}
