package com.example.demo.models;

import java.util.Arrays;
import java.util.Optional;

public enum Direction {
    ASCENDING("asc"),
    DESCENDING("desc");

    private String description;

    Direction(String description) {
        this.description = description;
    }

    public static Optional<Direction> fromDescription(String description) {
        return Arrays.stream(values()).filter(direction -> direction.description.equalsIgnoreCase(description)).findFirst();
    }
}