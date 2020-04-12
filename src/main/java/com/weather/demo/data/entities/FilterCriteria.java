package com.example.demo.data.entities;

import com.example.demo.models.DateRangeModel;
import com.example.demo.models.SearchModel;

import java.time.LocalDate;
import java.util.function.BiPredicate;

public class FilterCriteria {

    public final static BiPredicate<?, ?> ALWAYS_TRUE_PREDICATE = (a, b) -> true;

    private static BiPredicate<ClimateSummary, LocalDate> readingDateIsEqual = (climateSummary, localDate) ->
            climateSummary.getReadingDate().isEqual(localDate);

    private static BiPredicate<ClimateSummary, LocalDate> readingDateIsBefore = (climateSummary, localDate) ->
            climateSummary.getReadingDate().isBefore(localDate);

    private static BiPredicate<ClimateSummary, LocalDate> readingDateIsAfter = (climateSummary, localDate) ->
            climateSummary.getReadingDate().isAfter(localDate);

    private static BiPredicate<ClimateSummary, SearchModel> isReadingDateWithinRange = (climateSummary, searchModel) -> {
        if (searchModel == null) return true;
        DateRangeModel dateRangeModel = searchModel.getDateRange();
        if (!dateRangeModel.getFromDate().isPresent() || !dateRangeModel.getToDate().isPresent()) return true;

        return (readingDateIsEqual.test(climateSummary, dateRangeModel.getFromDate().get()) || readingDateIsAfter.test(climateSummary, dateRangeModel.getFromDate().get()))
                &&
                (readingDateIsEqual.test(climateSummary, dateRangeModel.getToDate().get()) || readingDateIsBefore.test(climateSummary, dateRangeModel.getToDate().get()));

    };

    public static BiPredicate getDateFilters(SearchModel searchModel) {

        if (searchModel == null) return ALWAYS_TRUE_PREDICATE;
        DateRangeModel dateRangeModel = searchModel.getDateRange();

        if (dateRangeModel == null
                || !dateRangeModel.getFromDate().isPresent()
                || !dateRangeModel.getToDate().isPresent()
        ) return ALWAYS_TRUE_PREDICATE;

        return isReadingDateWithinRange;

    }

    public static BiPredicate<ClimateSummary, SearchModel> exists = (climateSummary, searchModel) -> {
        if (climateSummary == null || searchModel == null || searchModel.getValue() == null || searchModel.getValue().isEmpty())
            return true;
        String searchValue = searchModel.getValue().trim().toLowerCase();
        if (searchValue.startsWith("@") && searchValue.endsWith("@") && searchValue.length() > 2) {
            searchValue = searchValue.substring(1, searchValue.length() - 1);
            return climateSummary.getStationName().equalsIgnoreCase(searchValue) || climateSummary.getSearchAssistReadingDate().equalsIgnoreCase(searchValue) || climateSummary.getSearchAssistMeanTemperature().equalsIgnoreCase(searchValue);
        } else
            return climateSummary.getStationName().toLowerCase().contains(searchValue) || climateSummary.getSearchAssistReadingDate().toLowerCase().contains(searchValue) || climateSummary.getSearchAssistMeanTemperature().toLowerCase().contains(searchValue);
    };

}
