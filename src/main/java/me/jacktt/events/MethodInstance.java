package me.jacktt.events;

import java.lang.reflect.Method;

public class MethodInstance {

    private Method method;
    private Listener listener;

    public MethodInstance(Listener listener, Method method) {
        this.listener = listener;
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public Listener getListener() {
        return listener;
    }
}
