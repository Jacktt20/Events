package me.jacktt.events;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Event {

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
            Field field = event.getClass().getDeclaredField("methods");
            field.setAccessible(true);
            List<MethodInstance> methods = (List<MethodInstance>) field.get(event);
            for(MethodInstance methodInstance : methods) {
                Method method = methodInstance.getMethod();
                if(event.isCancelled()) return;
                method.invoke(methodInstance.getListener(), event);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addListener(Listener listener) {
        Class<?> clazz = listener.getClass();
        for(Method method : clazz.getMethods()) {
            try {
                if(!method.isAnnotationPresent(EventMethod.class)) continue;
                EventPriority priority = method.getAnnotation(EventMethod.class).priority();
                if(method.getParameterTypes().length != 1) continue;
                if(Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    Field field = method.getParameterTypes()[0].getDeclaredField("methods");
                    field.setAccessible(true);
                    List<MethodInstance> list = (List<MethodInstance>) field.get(method.getParameterTypes()[0].getClass());
                    list.add(new MethodInstance(listener, method));
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
