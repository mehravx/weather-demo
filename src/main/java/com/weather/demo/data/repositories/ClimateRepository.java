package com.weather.demo.data.repositories;

import java.util.List;

public interface ClimateRepository<T> extends PagingSortingRepository<T> {

    public List<T> findByStationName(String stationName);

}