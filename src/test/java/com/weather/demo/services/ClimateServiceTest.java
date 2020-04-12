package com.example.demo.services;

import com.example.demo.data.entities.ClimateSummary;
import com.example.demo.data.repositories.ClimateRepository;
import com.example.demo.models.ClimateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DisplayName("Climate Service Test")
public class ClimateServiceTest {

    @InjectMocks
    ClimateService climateService;

    @Mock
    ClimateRepository climateRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("retrieve climate details by station name -> no match")
    @Test
    public void retrieveDetailsByName_no_match() {
        when(climateRepository.findByStationName(eq("Name"))).thenReturn(Collections.emptyList());
        List<ClimateModel> response = climateService.retrieveClimateDetails("Name");
        assertThat(response.isEmpty(), is(true));
    }

    @DisplayName("retrieve climate details by station name -> single match")
    @Test
    public void retrieveDetailsByName_singleMatch() {
        String stationName = "Single";

        ClimateSummary climateSummary = new ClimateSummary();
        climateSummary.setStationName(stationName);
        climateSummary.setProvince("BC");
        climateSummary.setReadingDate(LocalDate.of(2019, 05, 05));
        climateSummary.setMeanTemperature(10.0);
        List<ClimateSummary> climateSummaries = new ArrayList<>();
        climateSummaries.add(climateSummary);

        when(climateRepository.findByStationName(eq(stationName))).thenReturn(climateSummaries);
        List<ClimateModel> response = climateService.retrieveClimateDetails(stationName);

        assertThat(response.size(), is(1));
        assertThat(response.get(0).getStationName(), is(stationName));
        assertThat(response.get(0).getProvince().getDescription(), is("British Columbia"));
        assertThat(response.get(0).getProvince(), is(ClimateModel.Province.BC));
        assertThat(response.get(0).getReadingDate(), is(LocalDate.of(2019, 05, 05)));
        assertThat(response.get(0).getMeanTemperature().get(), is(10.0));
        assertThat(response.get(0).getMaxMonthlyTemperature().isPresent(), is(false));
        assertThat(response.get(0).getMaxMonthlyTemperature().isPresent(), is(false));

    }

    @DisplayName("retrieve climate details by station name -> multiple match")
    @Test
    public void retrieveDetailsByName_multiple_Match() {
        String stationName = "Multiple";

        ClimateSummary climateSummary = new ClimateSummary();
        climateSummary.setStationName(stationName);
        climateSummary.setProvince("BC");
        climateSummary.setReadingDate(LocalDate.of(2019, 05, 05));
        climateSummary.setMeanTemperature(10.0);

        ClimateSummary anotherClimateSummary = new ClimateSummary();
        anotherClimateSummary.setStationName(stationName);
        anotherClimateSummary.setProvince("ON");
        anotherClimateSummary.setReadingDate(LocalDate.of(2018, 05, 05));
        anotherClimateSummary.setMeanTemperature(-8.0);

        List<ClimateSummary> climateSummaries = new ArrayList<>();
        climateSummaries.add(climateSummary);
        climateSummaries.add(anotherClimateSummary);


        when(climateRepository.findByStationName(eq(stationName))).thenReturn(climateSummaries);
        List<ClimateModel> response = climateService.retrieveClimateDetails(stationName);

        assertThat(response.size(), is(2));

        assertThat(response.get(0).getStationName(), is(stationName));
        assertThat(response.get(0).getProvince().getDescription(), is("British Columbia"));
        assertThat(response.get(0).getProvince(), is(ClimateModel.Province.BC));
        assertThat(response.get(0).getReadingDate(), is(LocalDate.of(2019, 05, 05)));
        assertThat(response.get(0).getMeanTemperature().get(), is(10.0));
        assertThat(response.get(0).getMaxMonthlyTemperature().isPresent(), is(false));
        assertThat(response.get(0).getMaxMonthlyTemperature().isPresent(), is(false));

        assertThat(response.get(1).getStationName(), is(stationName));
        assertThat(response.get(1).getProvince().getDescription(), is("Ontario"));
        assertThat(response.get(1).getProvince(), is(ClimateModel.Province.ON));
        assertThat(response.get(1).getReadingDate(), is(LocalDate.of(2018, 05, 05)));
        assertThat(response.get(1).getMeanTemperature().get(), is(-8.0));
        assertThat(response.get(1).getMaxMonthlyTemperature().isPresent(), is(false));
        assertThat(response.get(1).getMaxMonthlyTemperature().isPresent(), is(false));

    }

}
