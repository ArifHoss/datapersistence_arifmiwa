package com.example.datapersistence_arifmiwa.model;

import java.util.List;

public class CustomerCountry {
    private List<Integer>  customerId;
    private String country;

    public CustomerCountry() {
    }

    public CustomerCountry(List<Integer> customerId, String country) {
        this.customerId = customerId;
        this.country = country;
    }

    public List<Integer> getCustomerId() {
        return customerId;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "List of customerId = " + customerId +
                ", country = '" + country + "' has most customers.";
    }
}
