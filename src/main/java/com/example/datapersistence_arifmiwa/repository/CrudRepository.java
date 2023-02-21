package com.example.datapersistence_arifmiwa.repository;

import com.example.datapersistence_arifmiwa.exception.CustomIllegalStateException;

import java.util.List;

public interface CrudRepository <T,U>{
    List<T> findAll();

    T findById(U id) throws CustomIllegalStateException;


    void save(T object);

    void update(T object);

    void delete(U id);
}
