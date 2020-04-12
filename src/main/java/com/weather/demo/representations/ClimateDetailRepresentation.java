package com.weather.demo.representations;

import com.weather.demo.models.ClimateModel;
import com.weather.demo.representations.views.DataViews;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClimateDetailRepresentation {

    @JsonView(DataViews.ClimateSummaryView.class)
    private String stationName;
    @JsonView(DataViews.ClimateSummaryView.class)
    private String meanMonthlyTemperature;
    @JsonView(DataViews.ClimateSummaryView.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate readingDate;
    private String maxMonthlyTemperature;
    private String minMonthlyTemperature;
    private String province;

    public ClimateDetailRepresentation() {
    }

    public ClimateDetailRepresentation(String stationName, LocalDate readingDate, String province) {
        this.stationName = stationName;
        this.readingDate = readingDate;
        this.province = province;
    }

    public ClimateDetailRepresentation withMaxMonthlyTemperature(Optional<Double> maxMonthlyTemperature) {
        this.maxMonthlyTemperature = maxMonthlyTemperature.isPresent() ? String.valueOf(maxMonthlyTemperature.get()) : "NA";
        return this;
    }

    public ClimateDetailRepresentation withMinMonthlyTemperature(Optional<Double> minMonthlyTemperature) {
        this.minMonthlyTemperature = minMonthlyTemperature.isPresent() ? String.valueOf(minMonthlyTemperature.get()) : "NA";
        return this;
    }

    public ClimateDetailRepresentation withMeanMonthlyTemperature(Optional<Double> meanMonthlyTemperature) {
        this.meanMonthlyTemperature = meanMonthlyTemperature.isPresent() ? String.valueOf(meanMonthlyTemperature.get()) : "NA";
        return this;
    }

    public String getStationName() {
        return stationName;
    }

    public String getMeanMonthlyTemperature() {
        return meanMonthlyTemperature;
    }

    public LocalDate getReadingDate() {
        return readingDate;
    }

    public String getMaxMonthlyTemperature() {
        return maxMonthlyTemperature;
    }

    public String getMinMonthlyTemperature() {
        return minMonthlyTemperature;
    }

    public String getProvince() {
        return province;
    }

    @Override
    public String toString() {
        return "ClimateDetailRepresentation{" +
                "stationName='" + stationName + '\'' +
                ", meanMonthlyTemperature='" + meanMonthlyTemperature + '\'' +
                ", readingDate=" + readingDate +
                ", maxMonthlyTemperature='" + maxMonthlyTemperature + '\'' +
                ", minMonthlyTemperature='" + minMonthlyTemperature + '\'' +
                ", province='" + province + '\'' +
                '}';
    }

    public static Function<ClimateModel, ClimateDetailRepresentation> toClimateDetail = climateModel ->
            new ClimateDetailRepresentation(
                    climateModel.getStationName(),
                    climateModel.getReadingDate(),
                    climateModel.getProvince().getDescription()
            )
                    .withMaxMonthlyTemperature(climateModel.getMaxMonthlyTemperature())
                    .withMinMonthlyTemperature(climateModel.getMinMonthlyTemperature())
                    .withMeanMonthlyTemperature(climateModel.getMeanTemperature());


    public static Function<List<ClimateModel>, List<ClimateDetailRepresentation>> toClimateDetails = climateModels ->
            climateModels.stream().map(toClimateDetail).collect(Collectors.toList());

}
