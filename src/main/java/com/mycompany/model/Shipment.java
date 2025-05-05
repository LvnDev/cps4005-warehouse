package com.mycompany.model;

public class Shipment {
    private int id;
    private String destination;
    private String shipmentDate;
    private String status;

    public Shipment(int id, String destination, String shipmentDate, String status) {
        this.id = id;
        this.destination = destination;
        this.shipmentDate = shipmentDate;
        this.status = status;
    }
    public Shipment(String destination, String shipmentDate, String status) {
        this(-1, destination, shipmentDate, status);
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getShipmentDate() { return shipmentDate; }
    public void setShipmentDate(String shipmentDate) { this.shipmentDate = shipmentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%d: %s | %s | %s",
            id, destination, shipmentDate, status);
    }
}

