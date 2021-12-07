package hello.proxy.pureproxy.proxy.code;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CacheProxy implements Subject {

    private final Subject subject;
    private String cacheValue;

    @Override
    public String operation() {
        log.info("프록시 호출");
        if (Objects.isNull(cacheValue)) {
            cacheValue = subject.operation();
        }

        return cacheValue;
    }
}
