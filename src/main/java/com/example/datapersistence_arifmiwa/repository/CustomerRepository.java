package com.example.datapersistence_arifmiwa.repository;


import com.example.datapersistence_arifmiwa.model.Customer;
import com.example.datapersistence_arifmiwa.model.CustomerGenre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

    Set<Customer> findByName(String name);
    List<Customer> findAllWithLimit(int limit, int offset);
    String getCountryWithMostCustomers();
    CustomerGenre getMostPopularGenres(int customerId);
}
