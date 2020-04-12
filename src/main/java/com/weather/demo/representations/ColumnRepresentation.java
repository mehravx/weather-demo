package com.weather.demo.representations;

import com.weather.demo.models.ColumnModel;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ColumnRepresentation {

    private String data;
    private String name;
    private Boolean searchable;
    private Boolean orderable;
    private SearchRepresentation searchRepresentation;

    public ColumnRepresentation() {
    }

    public ColumnRepresentation(String data, String name, Boolean searchable, Boolean orderable, SearchRepresentation searchRepresentation) {
        this.data = data;
        this.name = name;
        this.searchable = searchable;
        this.orderable = orderable;
        this.searchRepresentation = searchRepresentation;
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

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean getOrderable() {
        return orderable;
    }

    public void setOrderable(Boolean orderable) {
        this.orderable = orderable;
    }

    public SearchRepresentation getSearchRepresentation() {
        return searchRepresentation;
    }

    public void setSearchRepresentation(SearchRepresentation searchRepresentation) {
        this.searchRepresentation = searchRepresentation;
    }

    public static Function<ColumnRepresentation, ColumnModel> toColumnModel = columnRepresentation ->
            new ColumnModel(
                    columnRepresentation.getData(),
                    columnRepresentation.getName(),
                    columnRepresentation.getSearchable(),
                    columnRepresentation.getOrderable(),
                    columnRepresentation.getSearchRepresentation() == null ?
                            null : SearchRepresentation.toSearchModel.apply(columnRepresentation.getSearchRepresentation())
            );

    public static Function<List<ColumnRepresentation>, List<ColumnModel>> toColumns = columnRepresentations ->
            columnRepresentations.stream().map(toColumnModel).collect(Collectors.toList());
}