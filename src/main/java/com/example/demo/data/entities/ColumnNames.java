package com.example.demo.data.entities;

import java.util.Arrays;
import java.util.Optional;

public enum ColumnNames {

    STATION_NAME("stationName"),
    DATE("readingDate"),
    MEAN_TEMP("meanMonthlyTemperature");

    private String description;

    ColumnNames(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<ColumnNames> fromDescription(String description) {
        return Arrays.stream(values()).filter(columnName -> columnName.description.equalsIgnoreCase(description)).findFirst();
    }

}
