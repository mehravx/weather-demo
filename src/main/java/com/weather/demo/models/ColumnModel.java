package com.example.demo.models;

public class ColumnModel {

    private String data;
    private String name;
    private Boolean isSearchable;
    private Boolean isOrderable;
    private SearchModel searchModel;

    public ColumnModel() {
    }

    public ColumnModel(String data, String name, Boolean isSearchable, Boolean isOrderable, SearchModel searchModel) {
        this.data = data;
        this.name = name;
        this.isSearchable = isSearchable;
        this.isOrderable = isOrderable;
        this.searchModel = searchModel;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsSearchable() {
        return isSearchable;
    }

    public void setIsSearchable(Boolean isSearchable) {
        this.isSearchable = isSearchable;
    }

    public Boolean getIsOrderable() {
        return isOrderable;
    }

    public void setIsOrderable(Boolean isOrderable) {
        this.isOrderable = isOrderable;
    }

    public SearchModel getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(SearchModel searchModel) {
        this.searchModel = searchModel;
    }
}