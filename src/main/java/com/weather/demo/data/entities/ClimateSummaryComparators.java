package com.example.demo.data.entities;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToDoubleFunction;

public class ClimateSummaryComparators {

    public static final Comparator<ClimateSummary> EMPTY_COMPARATOR = (e1, e2) -> 0;

    public static final Comparator<ClimateSummary> READING_DATE_COMPARATOR = Comparator.comparing(ClimateSummary::getReadingDate, Comparator.nullsLast(Comparator.naturalOrder()));
    public static final Comparator<ClimateSummary> STATION_NAME_COMPARATOR = Comparator.comparing(ClimateSummary::getStationName, Comparator.nullsLast(Comparator.naturalOrder()));

    public static final Comparator<ClimateSummary> MEAN_TEMPERATURE_COMPARATOR_ASC_NULLS_LAST = Comparator.comparingDouble(new ToDoubleFunction<ClimateSummary>() {
        @Override
        public double applyAsDouble(ClimateSummary value) {
            return (value == null || value.getMeanTemperature() == null) ? Double.MAX_VALUE : value.getMeanTemperature();
        }
    });

    public static final Comparator<ClimateSummary> MEAN_TEMPERATURE_COMPARATOR_DESC_NULLS_LAST = Comparator.comparingDouble(new ToDoubleFunction<ClimateSummary>() {
        @Override
        public double applyAsDouble(ClimateSummary value) {
            return (value == null || value.getMeanTemperature() == null) ? Integer.MIN_VALUE : value.getMeanTemperature();
        }
    });

    static class Key {

        private ColumnNames columnNames;
        private SortDirection sortDirection;

        public Key(ColumnNames columnNames, SortDirection sortDirection) {
            this.columnNames = columnNames;
            this.sortDirection = sortDirection;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return columnNames == key.columnNames &&
                    sortDirection == key.sortDirection;
        }

        @Override
        public int hashCode() {
            return Objects.hash(columnNames, sortDirection);
        }

    }

    private static final Map<Key, Comparator<ClimateSummary>> KEY_COMPARATORS = new ConcurrentHashMap<>();

    static {
        KEY_COMPARATORS.put(new Key(ColumnNames.STATION_NAME, SortDirection.ASCENDING), STATION_NAME_COMPARATOR);
        KEY_COMPARATORS.put(new Key(ColumnNames.STATION_NAME, SortDirection.DESCENDING), STATION_NAME_COMPARATOR.reversed());

        KEY_COMPARATORS.put(new Key(ColumnNames.DATE, SortDirection.ASCENDING), READING_DATE_COMPARATOR);
        KEY_COMPARATORS.put(new Key(ColumnNames.DATE, SortDirection.DESCENDING), READING_DATE_COMPARATOR.reversed());

        KEY_COMPARATORS.put(new Key(ColumnNames.MEAN_TEMP, SortDirection.ASCENDING), MEAN_TEMPERATURE_COMPARATOR_ASC_NULLS_LAST);
        KEY_COMPARATORS.put(new Key(ColumnNames.MEAN_TEMP, SortDirection.DESCENDING), MEAN_TEMPERATURE_COMPARATOR_DESC_NULLS_LAST.reversed());
    }

    /**
     * Defaults the column name to Station Name & the sort direction to ascending
     *
     * @param columnName
     * @param direction
     * @return
     */
    public static Comparator<ClimateSummary> getComparator(String columnName, String direction) {
        ColumnNames columnNames = ColumnNames.fromDescription(columnName).orElse(ColumnNames.STATION_NAME);
        SortDirection sortDirection = SortDirection.fromDescription(direction).orElse(SortDirection.ASCENDING);
        return KEY_COMPARATORS.getOrDefault(new Key(columnNames, sortDirection), EMPTY_COMPARATOR);
    }

}
