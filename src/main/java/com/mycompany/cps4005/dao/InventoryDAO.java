package com.mycompany.cps4005.dao;

import com.mycompany.cps4005.DatabaseManager;
import com.mycompany.cps4005.InventoryItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    public static void addItem(InventoryItem item) {
        String sql = "INSERT INTO inventory(item_name, item_quantity, item_location) VALUES(?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, item.getName());
            p.setInt(2, item.getQuantity());
            p.setString(3, item.getLocation());
            p.executeUpdate();
            System.out.println("Item added.");
        } catch (SQLException e) {
            System.err.println("Error adding item: " + e.getMessage());
        }
    }

    public static List<InventoryItem> getAllItems() {
        List<InventoryItem> list = new ArrayList<>();
        String sql = "SELECT * FROM inventory";
        try (Connection conn = DatabaseManager.connect();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new InventoryItem(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getInt("item_quantity"),
                    rs.getString("item_location")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving items: " + e.getMessage());
        }
        return list;
    }

    public static InventoryItem findById(int id) {
        String sql = "SELECT * FROM inventory WHERE item_id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return new InventoryItem(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getInt("item_quantity"),
                        rs.getString("item_location")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding item: " + e.getMessage());
        }
        return null;
    }

    public static void updateItem(InventoryItem item) {
        String sql = "UPDATE inventory SET item_name=?, item_quantity=?, item_location=? WHERE item_id=?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, item.getName());
            p.setInt(2, item.getQuantity());
            p.setString(3, item.getLocation());
            p.setInt(4, item.getId());
            p.executeUpdate();
            System.out.println("Item updated.");
        } catch (SQLException e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }

    public static void deleteItem(int id) {
        String sql = "DELETE FROM inventory WHERE item_id=?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
            System.out.println("Item deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting item: " + e.getMessage());
        }
    }
}
