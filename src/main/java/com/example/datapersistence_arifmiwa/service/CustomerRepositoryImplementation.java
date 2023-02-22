package com.example.datapersistence_arifmiwa.service;


import com.example.datapersistence_arifmiwa.model.Customer;
import com.example.datapersistence_arifmiwa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class CustomerRepositoryImplementation implements CustomerRepository {


    private final String url;
    private final String username;
    private final String password;

    public CustomerRepositoryImplementation(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
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
        return findACustomerById(id);
    }


    @Override
    public Set<Customer> findByName(String name) { // Denna method fungerar ej
        String sql = "SELECT * FROM customer WHERE first_name LIKE ?";
//        String sql = "SELECT * FROM customer WHERE first_name LIKE ? ESCAPE '\\'";
        Set<Customer> customer = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = Collections.singleton(new CustomerMapper().mapRow(resultSet, resultSet.getRow()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public List<Customer> findAllWithLimit(int limit, int offset) {
        String sql = "SELECT * FROM customer ORDER BY customer_id LIMIT ? OFFSET ?";

        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new CustomerMapper().mapRow(resultSet, resultSet.getRow());
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name,country, postal_code, phone, fax, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getFirst_name());
            statement.setString(2, customer.getLast_name());
            statement.setString(3, customer.getCountry());
            statement.setString(4, customer.getPostal_code());
            statement.setString(5, customer.getPhone());
            statement.setString(6, customer.getFax());
            statement.setString(7, customer.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Customer customer) {
//        Customer customer = findACustomerById(id);
        String sql = "UPDATE customer SET first_name = ?, last_name = ?, country = ?, postal_code = ?, phone = ?, fax = ?, email = ? WHERE customer_id = ?";
//        jdbcTemplate.update(sql, customer.first_name(), customer.last_name(), customer.company(), customer.address(), customer.city(), customer.stat(), customer.country(), customer.postal_code(), customer.phone(), customer.fax(), customer.email(), customer.id());

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getFirst_name());
            statement.setString(2, customer.getLast_name());
            statement.setString(3, customer.getCountry());
            statement.setString(4, customer.getPostal_code());
            statement.setString(5, customer.getPhone());
            statement.setString(6, customer.getFax());
            statement.setString(7, customer.getEmail());
            statement.setInt(8, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("No customer with id " + id + " found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private Customer findACustomerById(Integer id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        Customer customer = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
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

}

