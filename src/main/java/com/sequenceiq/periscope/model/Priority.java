package com.sequenceiq.periscope.model;

public class Priority implements Comparable<Priority> {

    public static final Priority HIGH = Priority.of(0);
    public static final Priority NORMAL = Priority.of(100);
    public static final Priority LOW = Priority.of(Integer.MAX_VALUE);

    private final int value;

    private Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Priority priority = (Priority) o;

        if (value != priority.value) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public static Priority of(int value) {
        return new Priority(value < 0 ? 0 : value);
    }

    @Override
    public int compareTo(Priority o) {
        return Integer.compare(value, o.value);
    }
}
