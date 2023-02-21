package com.example.datapersistence_arifmiwa.service;


import com.example.datapersistence_arifmiwa.model.Customer;
import com.example.datapersistence_arifmiwa.repository.CustomerRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService implements CustomerRepo {

    private final JdbcTemplate jdbcTemplate;

    public CustomerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, new CustomerMapper());
    }

    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CustomerMapper());

    }

    @Override
    public Customer findByName(String name) {
        String sql = "SELECT * FROM customer WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{name}, new CustomerMapper());
    }

    @Override
    public List<Customer> findAllWithLimit(int limit, int offset) {
        String sql = "SELECT * FROM customer ORDER BY customer_id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{limit, offset}, new CustomerMapper());
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, company, address, city, state, country, postal_code, phone, fax, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, customer.first_name(), customer.last_name(), customer.company(), customer.address(), customer.city(), customer.stat(), customer.country(), customer.postal_code(), customer.phone(), customer.fax(), customer.email());
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customer SET first_name = ?, last_name = ?, company = ?, address = ?, city = ?, state = ?, country = ?, postal_code = ?, phone = ?, fax = ?, email = ? WHERE customer_id = ?";
        jdbcTemplate.update(sql, customer.first_name(), customer.last_name(), customer.company(), customer.address(), customer.city(), customer.stat(), customer.country(), customer.postal_code(), customer.phone(), customer.fax(), customer.email(), customer.id());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        jdbcTemplate.update(sql, id);
    }

}
