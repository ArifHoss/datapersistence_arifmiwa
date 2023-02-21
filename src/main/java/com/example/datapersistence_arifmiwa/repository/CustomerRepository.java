package com.example.datapersistence_arifmiwa.repository;


import com.example.datapersistence_arifmiwa.model.Customer;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

    Customer findByName(String name);
    List<Customer> findAllWithLimit(int limit, int offset);
}
