package com.example.datapersistence_arifmiwa.service;


import com.example.datapersistence_arifmiwa.model.Customer;
import com.example.datapersistence_arifmiwa.model.CustomerCountry;
import com.example.datapersistence_arifmiwa.model.CustomerGenre;
import com.example.datapersistence_arifmiwa.model.CustomerSpender;
import com.example.datapersistence_arifmiwa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

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
    public Set<Customer> findByName(String name) {
        String sql = "SELECT * FROM customer WHERE first_name LIKE ?";
//        String sql = "SELECT * FROM customers WHERE first_name LIKE ? ESCAPE '\\'";
        Set<Customer> customers = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customers.add(new CustomerMapper().mapRow(resultSet, resultSet.getRow()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
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

    // Return the country with the most customers.
    @Override
    public CustomerCountry getCountryWithMostCustomers() {
        String sql = "SELECT COUNT(*) AS count, country FROM customer GROUP BY country ORDER BY count DESC LIMIT 1";
        List<Integer> customerIds = new ArrayList<>();
        String country = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                country = result.getString("country");
                int count = result.getInt("count");
                PreparedStatement customerStatement = connection.prepareStatement("SELECT customer_id FROM customer WHERE country = ?");
                customerStatement.setString(1, country);
                ResultSet customerResult = customerStatement.executeQuery();
                System.out.println("Total customer: " + count);
                while (customerResult.next()) {
                    customerIds.add(customerResult.getInt("customer_id"));
                }
            }
            return new CustomerCountry(customerIds, country);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CustomerSpender customerHasHighestSpender() {
        String sql = "SELECT c.customer_id, SUM(i.total) AS total_spent " +
                "FROM customer c " +
                "JOIN invoice i ON c.customer_id = i.customer_id " +
                "GROUP BY c.customer_id " +
                "ORDER BY total_spent DESC " +
                "LIMIT 1";

        int totalSpent = 0;
        int customerId = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                totalSpent = result.getInt("total_spent");
                customerId = result.getInt("customer_id");
            }
            return new CustomerSpender(customerId, totalSpent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Returns the most popular genre for a given customer based on their purchase history.
     * If there is a tie for the most popular genre, all genres with the same highest count are returned.
     *
     * @param customerId the ID of the customer to search for
     * @return a list of the most popular genres for the customer, or an empty list if the customer has no purchases
     */
    @Override
    public CustomerGenre getMostPopularGenres(int customerId) {
        String sql = "SELECT ge.name, COUNT(*) as frequency " +
                "FROM invoice inv " +
                "JOIN invoice_line inv_line ON inv_line.invoice_id = inv.invoice_id " +
                "JOIN track tr ON tr.track_id = inv_line.track_id " +
                "JOIN genre ge ON ge.genre_id = tr.genre_id " +
                "WHERE inv.customer_id = ? " +
                "GROUP BY ge.name " +
                "HAVING COUNT(*) >= ALL (" +
                "  SELECT COUNT(*) " +
                "  FROM invoice inv2 " +
                "  JOIN invoice_line inv_line2 ON inv_line2.invoice_id = inv2.invoice_id " +
                "  JOIN track tr2 ON tr2.track_id = inv_line2.track_id " +
                "  JOIN genre ge2 ON ge2.genre_id = tr2.genre_id " +
                "  WHERE inv2.customer_id = ? " +
                "  GROUP BY ge2.name" +
                ")";
        List<String> genres = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.setInt(2, customerId);
            ResultSet resultSet = statement.executeQuery();
            int highestCount = 0;
            while (resultSet.next()) {
                String genreName = resultSet.getString("name");
                int genreCount = resultSet.getInt("frequency");
                if (genreCount > highestCount) {
                    genres.clear();
                    genres.add(genreName);
                    highestCount = genreCount;
                } else if (genreCount == highestCount) {
                    genres.add(genreName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new CustomerGenre(customerId, genres);
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

