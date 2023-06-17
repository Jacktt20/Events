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
        return switch (this) {
            case HIGH -> "HIGH";
            case NORMAL -> "NORMAL";
            case LOW -> "LOW";
        };
    }
}
