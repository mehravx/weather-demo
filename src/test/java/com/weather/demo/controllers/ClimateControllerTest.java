package com.example.demo.controllers;

import com.example.demo.models.ClimateModel;
import com.example.demo.representations.ClimateDetailRepresentation;
import com.example.demo.services.ClimateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Index Controller")
@WebMvcTest(ClimateController.class)
public class ClimateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClimateService climateService;

    @DisplayName("find by station name -> non empty")
    @Test
    public void findByStationName_non_empty() throws Exception {
        ClimateModel model = ClimateModel.Builder.aClimateModel("StationName", "ON", LocalDate.of(2019, 05, 05))
                .withMaxMonthlyTemperature(10.0)
                .withMinMonthlyTemperature(null)
                .withMeanTemperature(null)
                .build();
        List<ClimateModel> climateModels = new ArrayList<>();
        climateModels.add(model);

        when(climateService.retrieveClimateDetails(any())).thenReturn(climateModels);

        ModelAndView modelAndView = mockMvc.perform(get("/detail")
                .param("stationName", "StationName")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getModelAndView();

        assertThat(modelAndView.getViewName(), is("detail"));

        List<ClimateDetailRepresentation> response = (List<ClimateDetailRepresentation>) modelAndView.getModel().get("climateDetails");

        assertThat(response.size(), is(1));

        ClimateDetailRepresentation responseRepresentation = response.get(0);

        assertThat(responseRepresentation.getStationName(), is("StationName"));
        assertThat(responseRepresentation.getProvince(), is("Ontario"));
        assertThat(responseRepresentation.getReadingDate(), is(LocalDate.of(2019, 05, 05)));
        assertThat(responseRepresentation.getMeanMonthlyTemperature(), is("NA"));
        assertThat(responseRepresentation.getMaxMonthlyTemperature(), is("10.0"));
        assertThat(responseRepresentation.getMinMonthlyTemperature(), is("NA"));

    }

    @DisplayName("find by station name -> empty")
    @Test
    public void findByStationName_empty() throws Exception {

        when(climateService.retrieveClimateDetails(any())).thenReturn(Collections.EMPTY_LIST);

        ModelAndView modelAndView = mockMvc.perform(get("/detail")
                .param("stationName", "StationName")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getModelAndView();

        assertThat(modelAndView.getViewName(), is("detail"));

        List<ClimateDetailRepresentation> response = (List<ClimateDetailRepresentation>) modelAndView.getModel().get("climateDetails");

        assertThat(response.size(), is(0));

    }

    @DisplayName("find by station name -> missing parameter")
    @Test
    public void findByStationName_missing_param() throws Exception {

        when(climateService.retrieveClimateDetails(any())).thenReturn(Collections.EMPTY_LIST);

        mockMvc.perform(get("/detail")
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("message", is("Required String parameter 'stationName' is not present")));

    }

    @DisplayName("find by station name -> wrong parameter")
    @Test
    public void findByStationName_wrong_param() throws Exception {

        when(climateService.retrieveClimateDetails(any())).thenReturn(Collections.EMPTY_LIST);

        mockMvc.perform(get("/detail")
                .param("stationName1", "StationName")
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("message", is("Required String parameter 'stationName' is not present")));

    }

}
