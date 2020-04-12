package com.weather.demo.representations;

import com.weather.demo.models.SearchModel;

import java.util.function.Function;

public class SearchRepresentation {

    private String value;
    private String regexp;
    private DateRangeRepresentation dateRange;

    public SearchRepresentation() {
    }

    public SearchRepresentation(String value, String regexp, DateRangeRepresentation dateRange) {
        this.value = value;
        this.regexp = regexp;
        this.dateRange = dateRange;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public DateRangeRepresentation getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRangeRepresentation dateRange) {
        this.dateRange = dateRange;
    }

    public static Function<SearchRepresentation, SearchModel> toSearchModel = searchRepresentation ->
            new SearchModel(
                    searchRepresentation.getValue(),
                    searchRepresentation.getRegexp(),
                    DateRangeRepresentation.toDateModel.apply(searchRepresentation.getDateRange())
            );
}