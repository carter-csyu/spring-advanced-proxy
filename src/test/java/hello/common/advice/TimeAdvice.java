package hello.common.advice;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeAdvice called.");

        long start = System.currentTimeMillis();
        Object result = invocation.proceed();

        log.info("TimeAdvice | duration: {}ms", System.currentTimeMillis() - start);

        return result;
    }
}
