package com.example.demo.models;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

public class ClimateModel {

    public static enum Province {

        AB("Alberta"),
        BC("British Columbia"),
        MB("Manitoba"),
        NB("New Brunswick"),
        NL("Newfoundland And Labrador"),
        NS("Nova Scotia"),
        ON("Ontario"),
        PE("Prince Edward Island"),
        QC("Quebec"),
        SK("Saskatchewan"),
        NT("Northwest Territories"),
        NU("Nunavut"),
        YT("Yukon");

        private String description;

        Province(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public static ClimateModel.Province fromCode(String provinceCode) {
            return Arrays.stream(values()).filter(code -> code.name().equalsIgnoreCase(provinceCode)).findFirst().orElseThrow(() -> new RuntimeException("Invalid Province Code"));
        }

    }

    private String stationName;
    private Province province;
    private LocalDate readingDate;
    private Optional<Double> meanTemperature;
    private Optional<Double> maxMonthlyTemperature;
    private Optional<Double> minMonthlyTemperature;

    public static final class Builder {
        private String stationName;
        private String province;
        private LocalDate readingDate;
        private Double meanTemperature;
        private Double maxMonthlyTemperature;
        private Double minMonthlyTemperature;

        private Builder(String stationName, String province, LocalDate readingDate) {
            this.stationName = stationName;
            this.province = province;
            this.readingDate = readingDate;
        }

        public static Builder aClimateModel(String stationName, String province, LocalDate readingDate) {
            return new Builder(stationName, province, readingDate);
        }

        public Builder withMeanTemperature(Double meanTemperature) {
            this.meanTemperature = meanTemperature;
            return this;
        }

        public Builder withMaxMonthlyTemperature(Double maxMonthlyTemperature) {
            this.maxMonthlyTemperature = maxMonthlyTemperature;
            return this;
        }

        public Builder withMinMonthlyTemperature(Double minMonthlyTemperature) {
            this.minMonthlyTemperature = minMonthlyTemperature;
            return this;
        }

        public ClimateModel build() {
            return new ClimateModel(this);
        }
    }

    public ClimateModel(String stationName, String province, LocalDate readingDate) {
        this(stationName, province, readingDate, null, null, null);
    }

    private ClimateModel(Builder builder) {
        this(builder.stationName, builder.province, builder.readingDate, builder.meanTemperature, builder.maxMonthlyTemperature, builder.minMonthlyTemperature);
    }

    private ClimateModel(String stationName, String province, LocalDate readingDate, Double meanTemperature, Double maxMonthlyTemperature, Double minMonthlyTemperature) {
        this.stationName = stationName;
        this.province = Province.fromCode(province);
        this.readingDate = readingDate;
        this.meanTemperature = Optional.ofNullable(meanTemperature);
        this.maxMonthlyTemperature = Optional.ofNullable(maxMonthlyTemperature);
        this.minMonthlyTemperature = Optional.ofNullable(minMonthlyTemperature);
    }

    public String getStationName() {
        return stationName;
    }

    public Province getProvince() {
        return province;
    }

    public LocalDate getReadingDate() {
        return readingDate;
    }

    public Optional<Double> getMeanTemperature() {
        return meanTemperature;
    }

    public Optional<Double> getMaxMonthlyTemperature() {
        return maxMonthlyTemperature;
    }

    public Optional<Double> getMinMonthlyTemperature() {
        return minMonthlyTemperature;
    }

}
