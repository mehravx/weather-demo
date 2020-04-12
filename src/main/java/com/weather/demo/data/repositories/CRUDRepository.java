package com.example.demo.data.repositories;

import java.util.List;

public interface CRUDRepository<T> {

    public List<T> findAll();

    public int count();

}
