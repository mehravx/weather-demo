package com.weather.demo.data.entities;

import java.util.Arrays;
import java.util.Optional;

public enum SortDirection {

    ASCENDING("ASCENDING"),
    DESCENDING("DESCENDING");

    private String description;

    SortDirection(String description) {
        this.description = description;
    }

    public static Optional<SortDirection> fromDescription(String description) {
        return Arrays.stream(values()).filter(sortDirection -> sortDirection.description.equalsIgnoreCase(description)).findFirst();
    }
}
