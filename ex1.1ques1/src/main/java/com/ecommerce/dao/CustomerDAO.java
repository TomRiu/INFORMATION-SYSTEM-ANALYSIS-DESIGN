package com.ecommerce.dao;

import com.ecommerce.model.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/ecommerce";
    private String jdbcUsername = "springstudent";
    private String jdbcPassword = "springstudent"; // Change to your MySQL password

    private static final String SELECT_CUSTOMER_BY_USERNAME_AND_PASSWORD = 
        "SELECT * FROM customer WHERE username = ? AND password = ?";
    private static final String UPDATE_CUSTOMER_PASSWORD = 
        "UPDATE customer SET password = ? WHERE id = ?";
    private static final String SELECT_CUSTOMER_BY_EMAIL_OR_PHONE = 
        "SELECT * FROM customer WHERE email = ? OR phone = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Customer login(String username, String password) {
        Customer customer = null;
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_USERNAME_AND_PASSWORD)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setUsername(rs.getString("username"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public boolean updatePassword(int customerId, String newPassword) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setInt(2, customerId);

            rowUpdated = statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public Customer findCustomerByEmailOrPhone(String email, String phone) {
        Customer customer = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_EMAIL_OR_PHONE)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, phone);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setUsername(rs.getString("username"));
                customer.setPassword(rs.getString("password"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }
}
