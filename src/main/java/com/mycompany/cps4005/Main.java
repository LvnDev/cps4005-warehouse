package com.mycompany.cps4005;

import com.mycompany.cps4005.DatabaseManager;
import com.mycompany.cps4005.dao.InventoryDAO;
import com.mycompany.cps4005.dao.OrderDAO;
import com.mycompany.cps4005.dao.ShipmentDAO;
import com.mycompany.model.InventoryItem;
import com.mycompany.model.Order;
import com.mycompany.model.Shipment;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        Scanner in = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== St Marys Warehouse ===");
            System.out.println("1. Manage Inventory");
            System.out.println("2. Process Orders");
            System.out.println("3. Track Shipments");
            System.out.println("4. Exit");
            System.out.print("Choice: ");

            switch (in.nextLine()) {
                case "1": inventoryMenu(in); break;
                case "2": orderMenu(in);     break;
                case "3": shipmentMenu(in);  break;
                case "4": running = false;   break;
                default: System.out.println("Invalid choice.");
            }
        }
        in.close();
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
                case "a":
                    System.out.print("Name: "); String n = in.nextLine();
                    System.out.print("Qty: ");  int q = Integer.parseInt(in.nextLine());
                    System.out.print("Loc: ");  String l = in.nextLine();
                    InventoryDAO.addItem(new InventoryItem(n,q,l));
                    break;
                case "b":
                    List<InventoryItem> items = InventoryDAO.getAllItems();
                    items.forEach(System.out::println);
                    break;
                case "c":
                    System.out.print("ID to update: "); int ui = Integer.parseInt(in.nextLine());
                    InventoryItem it = InventoryDAO.findById(ui);
                    if (it != null) {
                        System.out.print("New Name ["+it.getName()+"]: "); String nn = in.nextLine();
                        System.out.print("New Qty ["+it.getQuantity()+"]: "); String nq = in.nextLine();
                        System.out.print("New Loc ["+it.getLocation()+"]: "); String nl = in.nextLine();
                        if (!nn.isEmpty()) it.setName(nn);
                        if (!nq.isEmpty()) it.setQuantity(Integer.parseInt(nq));
                        if (!nl.isEmpty()) it.setLocation(nl);
                        InventoryDAO.updateItem(it);
                    } else System.out.println("Not found.");
                    break;
                case "d":
                    System.out.print("ID to delete: "); int di = Integer.parseInt(in.nextLine());
                    System.out.print("Confirm? (y/n): "); if (in.nextLine().equalsIgnoreCase("y"))
                        InventoryDAO.deleteItem(di);
                    break;
                case "e": back = true; break;
                default: System.out.println("Invalid.");
            }
        }
    }

    private static void orderMenu(Scanner in) {
        String[] orderStatuses = { "Pending", "Shipped", "Completed" };

        boolean back = false;
        while (!back) {
            System.out.println("\n-- Orders --");
            System.out.println("a. Add Order");
            System.out.println("b. List All Orders");
            System.out.println("c. Update Order");
            System.out.println("d. Delete Order");
            System.out.println("e. Back");
            System.out.print("Choice: ");

            switch (in.nextLine()) {
            case "a":
                String date = promptForDate(in, "Order Date (YYYY-MM-DD): ");
                System.out.print("Customer Name: ");
                String customer = in.nextLine();
                String status = promptForOption(in, "Order Status:", orderStatuses);
                OrderDAO.addOrder(new Order(date, customer, status));
                break;

            case "b":
                List<Order> all = OrderDAO.getAllOrders();
                all.forEach(System.out::println);
                break;

            case "c":
                System.out.print("Order ID to update: ");
                int uid = Integer.parseInt(in.nextLine());
                Order o = OrderDAO.findById(uid);
                if (o != null) {
                    // reuse prompts but allow blank to keep old
                    String nd = promptForDateOrSkip(in, "New Date ["+o.getOrderDate()+"] (YYYY-MM-DD or enter to keep): ", o.getOrderDate());
                    System.out.print("New Customer ["+o.getCustomerName()+"]: ");
                    String nc = in.nextLine();
                    String ns = promptForOptionOrSkip(in, "New Status:", orderStatuses, o.getStatus());

                    o.setOrderDate(nd);
                    if (!nc.isEmpty()) o.setCustomerName(nc);
                    o.setStatus(ns);
                    OrderDAO.updateOrder(o);
                } else {
                    System.out.println("Order not found.");
                }
                break;

            case "d":
                System.out.print("Order ID to delete: ");
                int did = Integer.parseInt(in.nextLine());
                System.out.print("Confirm delete (y/n): ");
                if (in.nextLine().equalsIgnoreCase("y")) {
                    OrderDAO.deleteOrder(did);
                }
                break;

            case "e":
                back = true;
                break;

            default:
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void shipmentMenu(Scanner in) {
        String[] shipStatuses = { "Order Placed", "Packed", "In Transit", "Delivered" };

        boolean back = false;
        while (!back) {
            System.out.println("\n-- Shipments --");
            System.out.println("a. Add Shipment");
            System.out.println("b. List All Shipments");
            System.out.println("c. Update Shipment");
            System.out.println("d. Delete Shipment");
            System.out.println("e. Back");
            System.out.print("Choice: ");

            switch (in.nextLine()) {
            case "a":
                System.out.print("Destination: ");
                String dest = in.nextLine();
                String sdate = promptForDate(in, "Shipment Date (YYYY-MM-DD): ");
                String sstat = promptForOption(in, "Shipment Status:", shipStatuses);
                ShipmentDAO.addShipment(new Shipment(dest, sdate, sstat));
                break;

            case "b":
                List<Shipment> list = ShipmentDAO.getAllShipments();
                list.forEach(System.out::println);
                break;

            case "c":
                System.out.print("Shipment ID to update: ");
                int uid = Integer.parseInt(in.nextLine());
                Shipment s = ShipmentDAO.findById(uid);
                if (s != null) {
                    System.out.print("New Destination ["+s.getDestination()+"] (or enter to keep): ");
                    String nd = in.nextLine();
                    String nsd = promptForDateOrSkip(in, "New Date ["+s.getShipmentDate()+"] (YYYY-MM-DD or enter to keep): ", s.getShipmentDate());
                    String nss = promptForOptionOrSkip(in, "New Status:", shipStatuses, s.getStatus());

                    if (!nd.isEmpty()) s.setDestination(nd);
                    s.setShipmentDate(nsd);
                    s.setStatus(nss);
                    ShipmentDAO.updateShipment(s);
                } else {
                    System.out.println("Shipment not found.");
                }
                break;

            case "d":
                System.out.print("Shipment ID to delete: ");
                int did = Integer.parseInt(in.nextLine());
                System.out.print("Confirm delete (y/n): ");
                if (in.nextLine().equalsIgnoreCase("y")) {
                    ShipmentDAO.deleteShipment(did);
                }
                break;

            case "e":
                back = true;
                break;

            default:
                System.out.println("Invalid choice.");
            }
        }
    }

    /** Prompt until a valid YYYY-MM-DD date is entered. */
    private static String promptForDate(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine();
            try {
                LocalDate.parse(input);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("  → Invalid format. Please use YYYY-MM-DD.");
            }
        }
    }
    private static String promptForDateOrSkip(Scanner in, String prompt, String defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine();
            if (input.isEmpty()) return defaultValue;
            try {
                LocalDate.parse(input);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("  → Invalid format. Please use YYYY-MM-DD or leave blank.");
            }
        }
    }
    private static String promptForOption(Scanner in, String title, String[] options) {
        while (true) {
            System.out.println("  " + title);
            for (int i = 0; i < options.length; i++) {
                System.out.printf("    %d) %s%n", i + 1, options[i]);
            }
            System.out.print("Choice: ");
            try {
                int choice = Integer.parseInt(in.nextLine());
                if (choice >= 1 && choice <= options.length) {
                    return options[choice - 1];
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("  → Invalid choice; please enter 1–" + options.length);
        }
    }

    private static String promptForOptionOrSkip(Scanner in, String title, String[] options, String defaultValue) {
        while (true) {
            System.out.println("  " + title);
            for (int i = 0; i < options.length; i++) {
                System.out.printf("    %d) %s%n", i + 1, options[i]);
            }
            System.out.print("Choice (or Enter to keep \"" + defaultValue + "\"): ");
            String line = in.nextLine();
            if (line.isEmpty()) return defaultValue;
            try {
                int choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= options.length) {
                    return options[choice - 1];
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("  → Invalid choice; please enter 1–" + options.length + " or Enter.");
        }
    }
}