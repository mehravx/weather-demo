package com.example.demo.representations;

import com.example.demo.exceptions.ValidationException;
import com.example.demo.models.DateRangeModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.function.Function;

@JsonIgnoreProperties
public class DateRangeRepresentation {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy")
    private LocalDate fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy")
    private LocalDate toDate;

    private String columnName;

    public DateRangeRepresentation() {
    }

    public LocalDate getFromDate() {
        return fromDate == null ? null : LocalDate.of(fromDate.getYear(), fromDate.getMonthValue(), fromDate.getDayOfMonth());
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate == null ? null : LocalDate.of(fromDate.getYear(), fromDate.getMonthValue(), fromDate.getDayOfMonth());
    }

    public LocalDate getToDate() {
        return toDate == null ? null : LocalDate.of(toDate.getYear(), toDate.getMonthValue(), toDate.getDayOfMonth());
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate == null ? null : LocalDate.of(toDate.getYear(), toDate.getMonthValue(), toDate.getDayOfMonth());
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    private boolean validateDateRange() {
        return fromDate.isBefore(toDate) || fromDate.isEqual(toDate);
    }

    public static Function<DateRangeRepresentation, DateRangeModel> toDateModel = dateRangeRepresentation -> {
        if (dateRangeRepresentation.getFromDate() != null && dateRangeRepresentation.getToDate() != null && !dateRangeRepresentation.validateDateRange()) {
            ValidationException.ValidationError error = new ValidationException.ValidationError(10001, "Oops! Invalid date range! Server side!");
            ValidationException exception = new ValidationException("Validation Errors");
            exception.addValidationError(error);
            throw exception;
        }
        return new DateRangeModel(dateRangeRepresentation.getFromDate(), dateRangeRepresentation.getToDate(), dateRangeRepresentation.getColumnName());
    };

    @Override
    public String toString() {
        return "DateRange{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", columnName='" + columnName + '\'' +
                '}';
    }
}
