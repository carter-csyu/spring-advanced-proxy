package hello.proxy.pureproxy.concrete;

import hello.proxy.pureproxy.concrete.code.ConcreteClient;
import hello.proxy.pureproxy.concrete.code.ConcreteLogic;
import hello.proxy.pureproxy.concrete.code.TimeProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ConcreteProxyTest {

    @Test
    public void noProxy() {
        // given
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);

        // when
        client.execute();

        // then
    }

    @Test
    public void addProxy() {
        // given
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteLogic timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient client = new ConcreteClient(timeProxy);

        // when
        client.execute();

        // then
    }
}
