package com.weather.demo.integration.tests;

import com.weather.demo.WeatherApplication;
import com.weather.demo.data.entities.ColumnNames;
import com.weather.demo.representations.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WeatherApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("Climate Controller Integration Test")
public class ClimateControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("find by station name -> empty")
    @Test
    public void findByStationName_empty() throws Exception {

        ModelAndView modelAndView = mockMvc.perform(get("/detail")
                .param("stationName", "YORKTON")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getModelAndView();

        assertThat(modelAndView.getViewName(), is("detail"));

        List<ClimateDetailRepresentation> response = (List<ClimateDetailRepresentation>) modelAndView.getModel().get("climateDetails");

        assertThat(response.size(), is(0));

    }

    @DisplayName("find by station name -> non empty")
    @Test
    public void findByStationName_non_empty() throws Exception {

        ModelAndView modelAndView = mockMvc.perform(get("/detail")
                .param("stationName", "VISHAL")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getModelAndView();

        assertThat(modelAndView.getViewName(), is("detail"));

        List<ClimateDetailRepresentation> response = (List<ClimateDetailRepresentation>) modelAndView.getModel().get("climateDetails");

        assertThat(response.size(), is(1));
        assertThat(response.get(0).getStationName(), is("VISHAL"));

    }

    @DisplayName("search by station name -> non empty")
    @Test
    public void searchBy_station_name() throws Exception {

        DateRangeRepresentation dateRangeRepresentation = new DateRangeRepresentation();
        dateRangeRepresentation.setColumnName(ColumnNames.DATE.getDescription());

        SearchRepresentation searchRepresentation = new SearchRepresentation();
        searchRepresentation.setValue("NorTH");
        searchRepresentation.setDateRange(dateRangeRepresentation);

        OrderRepresentation orderRepresentation = new OrderRepresentation();
        orderRepresentation.setColumn(0);
        orderRepresentation.setDir(Direction.asc);

        List<OrderRepresentation> orderRepresentations = new ArrayList<>();
        orderRepresentations.add(orderRepresentation);

        ColumnRepresentation columnRepresentation = new ColumnRepresentation();
        columnRepresentation.setOrderable(true);
        columnRepresentation.setData(ColumnNames.STATION_NAME.getDescription());

        List<ColumnRepresentation> columnRepresentations = new ArrayList<>();
        columnRepresentations.add(columnRepresentation);

        PagingRequestRepresentation pagingRequestRepresentation = new PagingRequestRepresentation();
        pagingRequestRepresentation.setLength(7);
        pagingRequestRepresentation.setStart(0);
        pagingRequestRepresentation.setSearch(searchRepresentation);
        pagingRequestRepresentation.setOrder(orderRepresentations);
        pagingRequestRepresentation.setColumns(columnRepresentations);

        mockMvc.perform(post("/summary")
                .content(mapper.writeValueAsString(pagingRequestRepresentation))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].stationName", is("GALIANO NORTH")))
                .andExpect(jsonPath("$.data[1].stationName", is("NORTH COWICHAN")))
                .andExpect(jsonPath("$.data[2].stationName", is("NORTH COWICHAN")))
                .andExpect(jsonPath("$.data[0].meanMonthlyTemperature", is("14.9")))
                .andExpect(jsonPath("$.data[1].meanMonthlyTemperature", is("NA")))
                .andExpect(jsonPath("$.data[2].meanMonthlyTemperature", is("14.6")))
                .andExpect(jsonPath("$.data[0].readingDate", is("06/01/2018")))
                .andExpect(jsonPath("$.data[1].readingDate", is("06/01/2018")))
                .andExpect(jsonPath("$.data[2].readingDate", is("06/01/2018")))
                .andExpect(jsonPath("$.data[0].maxMonthlyTemperature").doesNotExist())
                .andExpect(jsonPath("$.data[1].maxMonthlyTemperature").doesNotExist())
                .andExpect(jsonPath("$.data[2].maxMonthlyTemperature").doesNotExist())
                .andExpect(jsonPath("$.data[0].minMonthlyTemperature").doesNotExist())
                .andExpect(jsonPath("$.data[1].minMonthlyTemperature").doesNotExist())
                .andExpect(jsonPath("$.data[2].minMonthlyTemperature").doesNotExist())
                .andExpect(jsonPath("$.recordsFiltered", is(3)))
                .andExpect(jsonPath("$.recordsTotal", is(7)));

    }

    @DisplayName("validation error -> date range -> error code 10001")
    @Test
    public void invalidDateRangeThrowsValidationException() throws Exception {

        DateRangeRepresentation dateRangeRepresentation = new DateRangeRepresentation();
        dateRangeRepresentation.setColumnName(ColumnNames.DATE.getDescription());
        dateRangeRepresentation.setFromDate(LocalDate.of(2018, 04, 01));
        dateRangeRepresentation.setToDate(LocalDate.of(2018, 03, 01));

        SearchRepresentation searchRepresentation = new SearchRepresentation();
        searchRepresentation.setValue("NorTH");
        searchRepresentation.setDateRange(dateRangeRepresentation);

        OrderRepresentation orderRepresentation = new OrderRepresentation();
        orderRepresentation.setColumn(0);
        orderRepresentation.setDir(Direction.asc);

        List<OrderRepresentation> orderRepresentations = new ArrayList<>();
        orderRepresentations.add(orderRepresentation);

        ColumnRepresentation columnRepresentation = new ColumnRepresentation();
        columnRepresentation.setOrderable(true);
        columnRepresentation.setData(ColumnNames.STATION_NAME.getDescription());

        List<ColumnRepresentation> columnRepresentations = new ArrayList<>();
        columnRepresentations.add(columnRepresentation);

        PagingRequestRepresentation pagingRequestRepresentation = new PagingRequestRepresentation();
        pagingRequestRepresentation.setLength(3);
        pagingRequestRepresentation.setStart(0);
        pagingRequestRepresentation.setSearch(searchRepresentation);
        pagingRequestRepresentation.setOrder(orderRepresentations);
        pagingRequestRepresentation.setColumns(columnRepresentations);

        mockMvc.perform(post("/summary")
                .content(mapper.writeValueAsString(pagingRequestRepresentation))
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation Errors")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.validationErrors[0].errorCode", is(10001)))
                .andExpect(jsonPath("$.validationErrors[0].errorMessage", is("Oops! Invalid date range! Server side!")));

    }


}
