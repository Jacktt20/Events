package me.jacktt;

import me.jacktt.events.*;
import me.jacktt.events.impl.ExampleEvent;

public class Main implements Listener {

    public Main() {
        Event.addListener(this);
        ExampleEvent event = new ExampleEvent("test");
        Event.runEvent(event);
    }

    @EventMethod(priority = EventPriority.NORMAL)
    public void doSomething(ExampleEvent event) {
        System.out.println("event: " + event.getText());
    }

    public static void main(String[] args) {
        new Main();
    }
}