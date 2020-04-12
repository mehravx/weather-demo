package com.example.demo.representations;

import com.example.demo.models.PagingRequestModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class PagingRequestRepresentation {

    private int start = 0;
    private int length = 10;
    private int draw;
    private List<OrderRepresentation> order;
    private List<ColumnRepresentation> columns;
    private SearchRepresentation search;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy")
    private LocalDate fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy")
    private LocalDate toDate;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public PagingRequestRepresentation() {
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

    public List<OrderRepresentation> getOrder() {
        return order;
    }

    public void setOrder(List<OrderRepresentation> order) {
        this.order = order;
    }

    public List<ColumnRepresentation> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnRepresentation> columns) {
        this.columns = columns;
    }

    public SearchRepresentation getSearch() {
        return search;
    }

    public void setSearch(SearchRepresentation search) {
        this.search = search;
    }

    public static Function<PagingRequestRepresentation, PagingRequestModel> toPagingModel = pagingRequestRepresentation ->
            new PagingRequestModel(
                    pagingRequestRepresentation.getStart(),
                    pagingRequestRepresentation.getLength(),
                    pagingRequestRepresentation.getDraw(),
                    OrderRepresentation.toOrderModels.apply(pagingRequestRepresentation.getOrder()),
                    ColumnRepresentation.toColumns.apply(pagingRequestRepresentation.getColumns()),
                    SearchRepresentation.toSearchModel.apply(pagingRequestRepresentation.getSearch())
            );

}