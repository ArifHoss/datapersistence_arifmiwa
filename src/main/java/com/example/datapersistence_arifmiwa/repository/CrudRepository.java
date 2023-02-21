package com.example.datapersistence_arifmiwa.repository;

import com.example.datapersistence_arifmiwa.exception.CustomIllegalStateException;
import com.example.datapersistence_arifmiwa.model.Customer;

import java.util.List;

public interface CrudRepository <T,U>{
    List<Customer> findAll();

    Customer findById(int id) throws CustomIllegalStateException;

    Customer findByName(String name);

    List<Customer> findAllWithLimit(int limit, int offset);

    void save(Customer customer);

    void update(Customer customer);

    void delete(int id);
}
