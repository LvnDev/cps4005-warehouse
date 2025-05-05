package com.mycompany.model;

public class InventoryItem {
    private int id;
    private String name;
    private int quantity;
    private String location;

    // Full-arg constructor (for loading from DB)
    public InventoryItem(int id, String name, int quantity, String location) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    // Convenience constructor (for new items, ID auto-generated)
    public InventoryItem(String name, int quantity, String location) {
        this(-1, name, quantity, location);
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return String.format("%d: %s (x%d) @ %s", id, name, quantity, location);
    }
}