package com.example.datapersistence_arifmiwa.model;

import java.util.List;

public class CustomerGenre {
    private int customerId;
    private List<String> genres;

    public CustomerGenre() {
    }

    public CustomerGenre(int customerId, List<String> genres) {
        this.customerId = customerId;
        this.genres = genres;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<String> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "CustomerGenre{" +
                "customerId=" + customerId +
                ", genres=" + genres +
                '}';
    }
}
