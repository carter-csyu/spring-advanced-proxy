package hello.proxy.pureproxy.concrete.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeProxy extends ConcreteLogic {

    private final ConcreteLogic concreteLogic;

    @Override
    public String operation() {
        log.info("TimeProxy called.");

        long start = System.currentTimeMillis();
        String result = concreteLogic.operation();
        log.info("TimeProxy | duration: {}ms", System.currentTimeMillis() - start);

        return result;
    }
}
