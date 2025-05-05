package com.mycompany.cps4005.dao;

import com.mycompany.cps4005.DatabaseManager;
import java.sql.*;

public class CustomerDAO {
    public static void addCustomer(String firstName, String lastName) {
        String sql = "INSERT INTO customers(first_name, last_name) VALUES(?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.executeUpdate();

            System.out.println("Customer added.");
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }
}
