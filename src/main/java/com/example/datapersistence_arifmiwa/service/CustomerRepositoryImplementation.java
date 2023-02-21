package com.example.datapersistence_arifmiwa.service;


import com.example.datapersistence_arifmiwa.model.Customer;
import com.example.datapersistence_arifmiwa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerRepositoryImplementation implements CustomerRepository {


    private final String url;
    private final String username;
    private final String password;

    public CustomerRepositoryImplementation(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}")String username,
            @Value("${spring.datasource.password}")String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Customer customer = new CustomerMapper().mapRow(resultSet, resultSet.getRow());
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        Customer customer = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            customer = new CustomerMapper().mapRow(resultSet, resultSet.getRow());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public Customer findByName(String name) {
        String sql = "SELECT * FROM customer WHERE name = ?";
        Customer customer = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = new CustomerMapper().mapRow(resultSet, resultSet.getRow());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public List<Customer> findAllWithLimit(int limit, int offset) {
        String sql = "SELECT * FROM customer ORDER BY customer_id LIMIT ? OFFSET ?";
//        return jdbcTemplate.query(sql, new Object[]{limit, offset}, new CustomerMapper());
        return null;
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, company, address, city, state, country, postal_code, phone, fax, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sql, customer.first_name(), customer.last_name(), customer.company(), customer.address(), customer.city(), customer.stat(), customer.country(), customer.postal_code(), customer.phone(), customer.fax(), customer.email());
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customer SET first_name = ?, last_name = ?, company = ?, address = ?, city = ?, state = ?, country = ?, postal_code = ?, phone = ?, fax = ?, email = ? WHERE customer_id = ?";
//        jdbcTemplate.update(sql, customer.first_name(), customer.last_name(), customer.company(), customer.address(), customer.city(), customer.stat(), customer.country(), customer.postal_code(), customer.phone(), customer.fax(), customer.email(), customer.id());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
//        jdbcTemplate.update(sql, id);
    }

}

