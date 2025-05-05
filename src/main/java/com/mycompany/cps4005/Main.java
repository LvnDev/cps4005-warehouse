package com.mycompany.cps4005;

import com.mycompany.cps4005.dao.CustomerDAO;
import com.mycompany.cps4005.dao.InventoryDAO;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        try (Scanner in = new Scanner(System.in)) {
            boolean running = true;
            
            while (running) {
                System.out.println("\n=== St Marys Warehouse ===");
                System.out.println("1. Manage Inventory");
                System.out.println("2. Process Orders");
                System.out.println("3. Track Shipments");
                System.out.println("4. Exit");
                System.out.print("Choice: ");
                
                switch (in.nextLine()) {
                    case "1" -> inventoryMenu(in);
                    case "2" -> {
                    }
                    case "3" -> {
                    }
                    case "4" -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
                            }
        }
    }

    private static void inventoryMenu(Scanner in) {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Inventory --");
            System.out.println("a. Add Item");
            System.out.println("b. List All Items");
            System.out.println("c. Update Item");
            System.out.println("d. Delete Item");
            System.out.println("e. Back");
            System.out.print("Choice: ");

            switch (in.nextLine()) {
                case "a" -> {
                    System.out.print("Name: ");
                    String name = in.nextLine();
                    System.out.print("Quantity: ");
                    int qty = Integer.parseInt(in.nextLine());
                    System.out.print("Location: ");
                    String loc = in.nextLine();
                    InventoryDAO.addItem(new InventoryItem(name, qty, loc));
                }
                case "b" -> {
                    List<InventoryItem> items = InventoryDAO.getAllItems();
                    items.forEach(i ->
                            System.out.printf("%d: %s (x%d) @ %s%n",
                                    i.getId(), i.getName(), i.getQuantity(), i.getLocation()));
                }
                case "c" -> {
                    System.out.print("Item ID to update: ");
                    int uid = Integer.parseInt(in.nextLine());
                    InventoryItem existing = InventoryDAO.findById(uid);
                    if (existing != null) {
                        System.out.print("New Name ["+existing.getName()+"]: ");
                        String nn = in.nextLine();
                        System.out.print("New Qty ["+existing.getQuantity()+"]: ");
                        String nq = in.nextLine();
                        System.out.print("New Loc ["+existing.getLocation()+"]: ");
                        String nl = in.nextLine();

                        existing.setName(nn.isEmpty() ? existing.getName() : nn);
                        existing.setQuantity(nq.isEmpty() ? existing.getQuantity() : Integer.parseInt(nq));
                        existing.setLocation(nl.isEmpty() ? existing.getLocation() : nl);
                        InventoryDAO.updateItem(existing);
                    } else {
                        System.out.println("Item not found.");
                    }
                }
                case "d" -> {
                    System.out.print("Item ID to delete: ");
                    int did = Integer.parseInt(in.nextLine());
                    System.out.print("Confirm delete? (y/n): ");
                    if (in.nextLine().equalsIgnoreCase("y")) {
                        InventoryDAO.deleteItem(did);
                    }
                }
                case "e" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}