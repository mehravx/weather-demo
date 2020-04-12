package com.weather.demo.data.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ClimateSummary {

    @JsonProperty("Station_Name")
    private String stationName;

    @JsonProperty("Province")
    private String province;

    private LocalDate readingDate;

    private Double meanTemperature;

    @JsonProperty("Highest_Monthly_Maxi_Temp")
    private Double maxMonthlyTemperature;

    @JsonProperty("Lowest_Monthly_Min_Temp")
    private Double minMonthlyTemperature;

    private String searchAssistMeanTemperature;
    private String searchAssistReadingDate;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public LocalDate getReadingDate() {
        return readingDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("Date")
    public void setReadingDate(LocalDate readingDate) {
        this.readingDate = readingDate;
        this.searchAssistReadingDate = readingDate == null ? "NA" : readingDate.getDayOfMonth() + "/" + readingDate.getMonthValue() + "/" + readingDate.getYear();
    }

    public Double getMeanTemperature() {
        return meanTemperature;
    }

    @JsonProperty("Mean_Temp")
    public void setMeanTemperature(Double meanTemperature) {
        this.meanTemperature = meanTemperature;
        this.searchAssistMeanTemperature = meanTemperature == null ? "NA" : String.valueOf(meanTemperature);
    }

    public Double getMaxMonthlyTemperature() {
        return maxMonthlyTemperature;
    }

    public void setMaxMonthlyTemperature(Double maxMonthlyTemperature) {
        this.maxMonthlyTemperature = maxMonthlyTemperature;
    }

    public Double getMinMonthlyTemperature() {
        return minMonthlyTemperature;
    }

    public void setMinMonthlyTemperature(Double minMonthlyTemperature) {
        this.minMonthlyTemperature = minMonthlyTemperature;
    }

    public String getSearchAssistMeanTemperature() {
        return searchAssistMeanTemperature;
    }

    public String getSearchAssistReadingDate() {
        return searchAssistReadingDate;
    }

    @Override
    public String toString() {
        return "ClimateSummary{" +
                "stationName='" + stationName + '\'' +
                ", province='" + province + '\'' +
                ", readingDate=" + readingDate +
                ", meanTemperature=" + meanTemperature +
                ", maxMonthlyTemperature=" + maxMonthlyTemperature +
                ", minMonthlyTemperature=" + minMonthlyTemperature +
                '}';
    }

}
