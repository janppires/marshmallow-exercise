package com.marshmallow.platform.core.common;

public enum Direction {
    NORTH('N'), EAST('E'), SOUTH('S'), WEST('W');

    private char value;

    Direction(char value) {
        this.value = value;
    }

    public static Direction findByChar(char str) {
        for (Direction direction : values()) {
            if(direction.value == str) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Unknown direction: " + str);
    }
}
