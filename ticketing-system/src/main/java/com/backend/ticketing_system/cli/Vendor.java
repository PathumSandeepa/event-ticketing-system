package com.backend.ticketing_system.cli;

public class Vendor {
    private String id;
    private String name;

    // Default constructor
    public Vendor() {}

    public Vendor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void releaseTickets(TicketPool pool) {
        pool.releaseTickets(1);
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