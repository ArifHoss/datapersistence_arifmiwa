package com.example.datapersistence_arifmiwa.model;

import java.util.List;

public class CustomerSpender {
    private int customerId;
    private int total;

    public CustomerSpender() {
    }


    public CustomerSpender(int customerId, int total) {
        this.customerId = customerId;
        this.total = total;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "CustomerID = " + customerId +
                ", total-spent = " + total;
    }
}
