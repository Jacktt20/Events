package me.jacktt.events.impl;

import me.jacktt.events.Event;
import me.jacktt.events.MethodInstance;

import java.util.ArrayList;
import java.util.List;

public class ExampleEvent extends Event {

    private String text;

    public ExampleEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
