package com.mycompany.cps4005;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:data/warehouse.db";
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    public static void initializeDatabase() {
        try {
            //Ensure the data/ directory exists
            Path dataDir = Paths.get("data");
            if (Files.notExists(dataDir)) {
                Files.createDirectory(dataDir);
            }

            System.out.println("Working dir: " + System.getProperty("user.dir"));
            System.out.println("Database file: " + dataDir.resolve("warehouse.db").toAbsolutePath());

            try (Connection conn = connect();
                 Statement stmt = conn.createStatement()) {

                // inventory
                stmt.execute(
                    "CREATE TABLE IF NOT EXISTS inventory (" +
                    "  item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  item_name TEXT    NOT NULL," +
                    "  item_quantity INTEGER NOT NULL," +
                    "  item_location TEXT    NOT NULL" +
                    ");"
                );

                // orders
                stmt.execute(
                    "CREATE TABLE IF NOT EXISTS orders (" +
                    "  order_id   INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  order_date TEXT    NOT NULL," +
                    "  customer_name TEXT NOT NULL," +
                    "  order_status  TEXT    NOT NULL" +
                    ");"
                );

                // shipments
                stmt.execute(
                    "CREATE TABLE IF NOT EXISTS shipments (" +
                    "  shipment_id     INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  destination     TEXT    NOT NULL," +
                    "  shipment_date   TEXT    NOT NULL," +
                    "  shipment_status TEXT    NOT NULL" +
                    ");"
                );

                System.out.println("All tables are set up.");
            }
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}