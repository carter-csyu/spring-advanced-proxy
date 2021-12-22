package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import java.lang.reflect.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicProxyBasicConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1 orderService = new OrderControllerV1Impl(orderService(logTrace));

        return (OrderControllerV1) Proxy.newProxyInstance(
            OrderControllerV1.class.getClassLoader(),
            new Class[]{OrderControllerV1.class},
            new LogTraceBasicHandler(orderService, logTrace));
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1 orderService = new OrderServiceV1Impl(orderRepository(logTrace));

        return (OrderServiceV1) Proxy.newProxyInstance(
            OrderServiceV1.class.getClassLoader(),
            new Class[]{OrderServiceV1.class},
            new LogTraceBasicHandler(orderService, logTrace));
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();

        return (OrderRepositoryV1) Proxy.newProxyInstance(
            OrderRepositoryV1.class.getClassLoader(),
            new Class[]{OrderRepositoryV1.class},
            new LogTraceBasicHandler(orderRepository, logTrace));
    }
}
