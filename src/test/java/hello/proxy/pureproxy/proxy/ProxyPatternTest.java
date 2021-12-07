package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    public void testNoProxy() {
        // given
        RealSubject subject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(subject);

        // when
        client.execute();
        client.execute();
        client.execute();

        // then
    }

    @Test
    public void testProxyCache() {
        // given
        RealSubject subject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(subject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        // when
        client.execute();
        client.execute();
        client.execute();

        // then
    }
}
