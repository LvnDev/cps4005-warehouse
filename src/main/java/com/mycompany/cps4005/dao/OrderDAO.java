package com.mycompany.cps4005.dao;
import com.mycompany.cps4005.DatabaseManager;
import com.mycompany.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public static void addOrder(Order o) {
        String sql = "INSERT INTO orders(order_date, customer_name, order_status) VALUES(?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, o.getOrderDate());
            p.setString(2, o.getCustomerName());
            p.setString(3, o.getStatus());
            p.executeUpdate();
            System.out.println("Order added.");
        } catch (SQLException e) {
            System.err.println("Error adding order: " + e.getMessage());
        }
    }

    public static List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = DatabaseManager.connect();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Order(
                    rs.getInt("order_id"),
                    rs.getString("order_date"),
                    rs.getString("customer_name"),
                    rs.getString("order_status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving orders: " + e.getMessage());
        }
        return list;
    }

    public static Order findById(int id) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                        rs.getInt("order_id"),
                        rs.getString("order_date"),
                        rs.getString("customer_name"),
                        rs.getString("order_status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding order: " + e.getMessage());
        }
        return null;
    }

    public static void updateOrder(Order o) {
        String sql = "UPDATE orders SET order_date=?, customer_name=?, order_status=? WHERE order_id=?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, o.getOrderDate());
            p.setString(2, o.getCustomerName());
            p.setString(3, o.getStatus());
            p.setInt(4, o.getId());
            p.executeUpdate();
            System.out.println("Order updated.");
        } catch (SQLException e) {
            System.err.println("Error updating order: " + e.getMessage());
        }
    }

    public static void deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE order_id=?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
            System.out.println("Order deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting order: " + e.getMessage());
        }
    }
}
