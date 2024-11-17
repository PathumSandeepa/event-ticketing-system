package com.backend.ticketing_system.cli;

public class Customer {
    private String id;
    private String name;

    // Default constructor
    public Customer() {}

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean purchaseTicket(TicketPool pool) {
        return pool.retrieveTicket();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}