package hello.proxy.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageDecorator implements Component {

    private final Component component;

    @Override
    public String operation() {
        log.info("MessageDecorator called.");

        String result = component.operation();
        String decorated = String.format("***%s***", result);
        log.info("MessageDecorator | before = {}, after = {}", result, decorated);

        return decorated;
    }
}
