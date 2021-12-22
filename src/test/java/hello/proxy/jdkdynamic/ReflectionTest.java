package hello.proxy.jdkdynamic;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공동 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result = {}", result1);
        // 공동 로직1 종료

        // 공동 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result = {}", result2);
        // 공동 로직2 종료
    }

    @Test
    void reflection1() throws Exception {
        Class clazz = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // 공동 로직1 시작
        log.info("start");
        Method methodCallA = clazz.getMethod("callA");
        String result1 = (String) methodCallA.invoke(target);
        log.info("result = {}", result1);
        // 공동 로직1 종료

        // 공동 로직2 시작
        log.info("start");
        Method methodCallB = clazz.getMethod("callB");
        String result2 = (String) methodCallB.invoke(target);
        log.info("result = {}", result2);
        // 공동 로직2 종료
    }

    @Test
    void reflection2() throws Exception {
        Class clazz = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // 공동 로직1 시작
        Method methodCallA = clazz.getMethod("callA");
        dynamicCall(methodCallA, target);
        // 공동 로직1 종료

        // 공동 로직2 시작
        Method methodCallB = clazz.getMethod("callB");
        dynamicCall(methodCallB, target);
        // 공동 로직2 종료
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        String result = (String) method.invoke(target);
        log.info("result = {}", result);
    }

    static class Hello {

        public String callA() {
            log.info("callA");

            return "A";
        }

        public String callB() {
            log.info("callB");

            return "B";
        }
    }
}