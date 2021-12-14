package hello.proxy.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeDecorator implements Component {

    private final Component component;

    @Override
    public String operation() {
        log.info("TimeDecorator called.");

        long start = System.currentTimeMillis();
        String result = component.operation();
        log.info("TimeDecorator | duration: {}ms", System.currentTimeMillis() - start);

        return result;
    }
}
