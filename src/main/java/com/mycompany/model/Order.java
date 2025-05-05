package com.mycompany.model;

public class Order {
    private int id;
    private String orderDate;
    private String customerName;
    private String status;

    public Order(int id, String orderDate, String customerName, String status) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.status = status;
    }
    public Order(String orderDate, String customerName, String status) {
        this(-1, orderDate, customerName, status);
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%d: %s | %s | %s",
            id, orderDate, customerName, status);
    }
}