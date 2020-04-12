package com.example.demo.models;

public class SearchModel {

    private String value;
    private String regexp;
    private DateRangeModel dateRange;

    public SearchModel() {
    }

    public SearchModel(String value, String regexp, DateRangeModel dateRange) {
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

    public DateRangeModel getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRangeModel dateRange) {
        this.dateRange = dateRange;
    }

}