package com.weather.demo.data.repositories;

import com.weather.demo.config.ClimateFileConfig;
import com.weather.demo.data.entities.ClimateSummary;
import com.weather.demo.data.entities.ColumnNames;
import com.weather.demo.models.ColumnModel;
import com.weather.demo.models.Direction;
import com.weather.demo.models.OrderModel;
import com.weather.demo.models.PagingRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@DisplayName("CSV Data Repository")
public class CSVDataRepositoryTest {

    CSVDataRepository csvDataRepository;
    ClimateFileConfig config = new ClimateFileConfig(null, "test.csv", ',');

    @BeforeEach
    public void init() {
        csvDataRepository = new CSVDataRepository(config);
        csvDataRepository.loadData();
    }

    @DisplayName("total count")
    @Test
    public void totalCount() {
        int count = csvDataRepository.count();
        assertThat(count, is(7));
    }

    @DisplayName("slice")
    @Test
    public void slice() {
        PagingRequestModel model = new PagingRequestModel();
        model.setStart(2);
        model.setLength(2);
        List<ClimateSummary> climateSummaries = csvDataRepository.findAll(model);

        assertThat(climateSummaries.size(), is(2));

    }

    @DisplayName("sorting -> by station name asc")
    @Test
    public void sort_by_station_name_asc() {
        PagingRequestModel model = new PagingRequestModel();
        OrderModel orderModel = new OrderModel();
        orderModel.setColumn(0);
        orderModel.setDir(Direction.ASCENDING);
        List<OrderModel> orderModels = new ArrayList<>();
        orderModels.add(orderModel);

        ColumnModel columnModel = new ColumnModel();
        columnModel.setIsOrderable(true);
        columnModel.setData(ColumnNames.STATION_NAME.getDescription());
        List<ColumnModel> columnModels = new ArrayList<>();
        columnModels.add(columnModel);

        model.setColumns(columnModels);
        model.setOrder(orderModels);

        List<ClimateSummary> climateSummaries = csvDataRepository.findAll(model);

        assertThat(climateSummaries.size(), is(7));
        assertThat(climateSummaries.get(0).getStationName(), is("ESQUIMALT HARBOUR"));
        assertThat(climateSummaries.get(2).getStationName(), is("MALAHAT"));
    }

}
