package com.sequenceiq.it.cloudbreak.newway;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.sequenceiq.it.cloudbreak.newway.log.Log.log;

public class ProxyHandler<I> implements InvocationHandler {

    private final I original;

    private final GenericProxyExecutor executor;

    public ProxyHandler(I original, GenericProxyExecutor executor) {
        this.original = original;
        this.executor = executor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        printArgs(method, args);
        method.setAccessible(true);
        return executor.exec(() -> method.invoke(original, args));
    }

    private void printArgs(Method method, Object[] args) {
        if (args != null) {
            String[] declaringClass = method.getDeclaringClass().toString().split(" ");
            String initLogMessage = declaringClass.length == 2
                    ? String.format("Method ['%s.%s'] called with args:%n", method.getDeclaringClass().toString().split(" ")[1], method.getName())
                    : String.format("Method ['%s'] called with args:%n", method.getName());
            log(initLogMessage);
            log(String.join(", ", Arrays.stream(args).map(o -> o == null ? "null" : o.toString()).collect(Collectors.toList())));
        }
    }
}
