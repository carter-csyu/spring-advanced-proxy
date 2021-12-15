package hello.proxy.pureproxy.concrete.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConcreteClient {

    private final ConcreteLogic concreteLogic;

    public void execute() {
        concreteLogic.operation();
    }
}
