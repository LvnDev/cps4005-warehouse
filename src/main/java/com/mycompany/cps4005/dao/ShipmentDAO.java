package com.mycompany.cps4005.dao;

import com.mycompany.cps4005.DatabaseManager;
import com.mycompany.model.Shipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDAO {
    public static void addShipment(Shipment s) {
        String sql = "INSERT INTO shipments(destination, shipment_date, shipment_status) VALUES(?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, s.getDestination());
            p.setString(2, s.getShipmentDate());
            p.setString(3, s.getStatus());
            p.executeUpdate();
            System.out.println("Shipment added.");
        } catch (SQLException e) {
            System.err.println("Error adding shipment: " + e.getMessage());
        }
    }

    public static List<Shipment> getAllShipments() {
        List<Shipment> list = new ArrayList<>();
        String sql = "SELECT * FROM shipments";
        try (Connection conn = DatabaseManager.connect();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Shipment(
                    rs.getInt("shipment_id"),
                    rs.getString("destination"),
                    rs.getString("shipment_date"),
                    rs.getString("shipment_status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving shipments: " + e.getMessage());
        }
        return list;
    }

    public static Shipment findById(int id) {
        String sql = "SELECT * FROM shipments WHERE shipment_id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return new Shipment(
                        rs.getInt("shipment_id"),
                        rs.getString("destination"),
                        rs.getString("shipment_date"),
                        rs.getString("shipment_status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding shipment: " + e.getMessage());
        }
        return null;
    }

    public static void updateShipment(Shipment s) {
        String sql = "UPDATE shipments SET destination=?, shipment_date=?, shipment_status=? WHERE shipment_id=?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, s.getDestination());
            p.setString(2, s.getShipmentDate());
            p.setString(3, s.getStatus());
            p.setInt(4, s.getId());
            p.executeUpdate();
            System.out.println("Shipment updated.");
        } catch (SQLException e) {
            System.err.println("Error updating shipment: " + e.getMessage());
        }
    }

    public static void deleteShipment(int id) {
        String sql = "DELETE FROM shipments WHERE shipment_id=?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
            System.out.println("Shipment deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting shipment: " + e.getMessage());
        }
    }
}
