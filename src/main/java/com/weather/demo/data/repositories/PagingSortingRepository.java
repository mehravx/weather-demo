package com.weather.demo.data.repositories;

import com.weather.demo.models.PagingRequestModel;

import java.util.List;

public interface PagingSortingRepository<T> extends CRUDRepository<T> {

    public List<T> findAll(PagingRequestModel pagingRequestModel);

    public int count(PagingRequestModel pagingRequestModel);

}
