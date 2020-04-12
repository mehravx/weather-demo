package com.weather.demo.representations;

import com.weather.demo.models.ClimateModel;
import com.weather.demo.models.PageModel;
import com.weather.demo.representations.views.DataViews;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.function.Function;

@JsonView(DataViews.ClimateSummaryView.class)
public class PageRepresentation<T> {

    public PageRepresentation(List<T> data) {
        this.data = data;
    }

    private List<T> data;
    private int recordsFiltered;
    private int recordsTotal;
    private int draw;

    public PageRepresentation() {
    }

    public PageRepresentation(List<T> data, int recordsFiltered, int recordsTotal, int draw) {
        this.data = data;
        this.recordsFiltered = recordsFiltered;
        this.recordsTotal = recordsTotal;
        this.draw = draw;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public static Function<PageModel<ClimateModel>, PageRepresentation<ClimateDetailRepresentation>> toPageRepresentation = pageModel ->
            new PageRepresentation<>(ClimateDetailRepresentation.toClimateDetails.apply(pageModel.getData()), pageModel.getRecordsFiltered(), pageModel.getRecordsTotal(), pageModel.getDraw());

    @Override
    public String toString() {
        return "PageRepresentation{" +
                "data=" + data +
                ", recordsFiltered=" + recordsFiltered +
                ", recordsTotal=" + recordsTotal +
                ", draw=" + draw +
                '}';
    }
}