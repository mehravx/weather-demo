package com.weather.demo.models;

import java.time.LocalDate;
import java.util.Optional;

public class DateRangeModel {

    private Optional<LocalDate> fromDate;
    private Optional<LocalDate> toDate;
    private Optional<String> columnName;

    public DateRangeModel() {
    }

    public DateRangeModel(LocalDate fromDate, LocalDate toDate, String columnName) {
        this.fromDate = Optional.ofNullable(fromDate);
        this.toDate = Optional.ofNullable(toDate);
        this.columnName = Optional.ofNullable(columnName);
    }

    public Optional<LocalDate> getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = Optional.ofNullable(fromDate);
    }

    public Optional<LocalDate> getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = Optional.of(toDate);
    }

    public Optional<String> getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = Optional.ofNullable(columnName);
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", columnName='" + columnName + '\'' +
                '}';
    }

}
