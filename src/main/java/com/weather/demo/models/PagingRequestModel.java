package com.weather.demo.models;

import java.util.List;

public class PagingRequestModel {

    private int start = 0;
    private int length = 10;
    private int draw;
    private List<OrderModel> order;
    private List<ColumnModel> columns;
    private SearchModel search;

    public PagingRequestModel() {
    }

    public PagingRequestModel(int start, int length, int draw, List<OrderModel> order, List<ColumnModel> columns, SearchModel search) {
        this.start = start;
        this.length = length;
        this.draw = draw;
        this.order = order;
        this.columns = columns;
        this.search = search;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public List<OrderModel> getOrder() {
        return order;
    }

    public void setOrder(List<OrderModel> order) {
        this.order = order;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public SearchModel getSearch() {
        return search;
    }

    public void setSearch(SearchModel search) {
        this.search = search;
    }
}