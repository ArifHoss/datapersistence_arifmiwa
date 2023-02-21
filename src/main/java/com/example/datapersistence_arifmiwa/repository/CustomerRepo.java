package com.example.datapersistence_arifmiwa.repository;



import com.example.datapersistence_arifmiwa.exception.CustomIllegalStateException;
import com.example.datapersistence_arifmiwa.model.Customer;

import java.util.List;

public interface CustomerRepo extends CrudRepository<Customer, Integer>{

}
