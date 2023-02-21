package com.example.datapersistence_arifmiwa.model;

public record Customer(int id,
                       String first_name,
                       String last_name,
                       String company,
                       String address,
                       String city,
                       String stat,
                       String country,
                       String postal_code,
                       String phone,
                       String fax,
                       String email) {
}
