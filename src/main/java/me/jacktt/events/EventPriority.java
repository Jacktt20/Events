package me.jacktt.events;

public enum EventPriority {
    HIGH(0),
    NORMAL(1),
    LOW(2);

    private int id;

    EventPriority(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        switch(this) {
            case HIGH:
                return "HIGH";
            case NORMAL:
                return "NORMAL";
            case LOW:
                return "LOW";
        }
        return "INVALID";
    }
}
