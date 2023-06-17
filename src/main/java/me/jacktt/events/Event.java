package me.jacktt.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Event {

    private static Map<Class<? extends Event>, PriorityList<MethodInstance>> listeners = new HashMap<>();

    private boolean cancelled;

    public Event() {
        cancelled = false;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static void runEvent(Event event) {
        try {
            for(MethodInstance methodInstance : listeners.get(event.getClass()).getList()) {
                Method method = methodInstance.getMethod();
                if(event.isCancelled()) return;
                method.invoke(methodInstance.getListener(), event);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addListener(Listener listener) {
        Class<?> clazz = listener.getClass();
        for(Method method : clazz.getMethods()) {
            if(!method.isAnnotationPresent(EventMethod.class)) continue;
            EventPriority priority = method.getAnnotation(EventMethod.class).priority();
            if(method.getParameterTypes().length != 1) continue;
            if(Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                PriorityList<MethodInstance> list = listeners.get(method.getParameterTypes()[0]);
                if(listeners.get(method.getParameterTypes()[0]) == null) list = new PriorityList<>();
                list.add(priority.getId(), new MethodInstance(listener, method));
                listeners.put((Class<? extends Event>) method.getParameterTypes()[0], list);
            }
        }
    }
}
