package com.example.demo.data.repositories;

import com.example.demo.models.PagingRequestModel;

import java.util.List;

public interface PagingSortingRepository<T> extends CRUDRepository<T> {

    public List<T> findAll(PagingRequestModel pagingRequestModel);

    public int count(PagingRequestModel pagingRequestModel);

}
