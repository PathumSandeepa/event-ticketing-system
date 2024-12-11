package com.backend.ticketing_system.cli;

import java.util.concurrent.atomic.AtomicBoolean;

// This class represents a vendor thread that adds tickets to the ticket pool at a certain rate until the ticket pool is at maximum capacity
// oop concepts used: Encapsulation, Inheritance, Polymorphism
public class VendorRunnable extends UserRunnable {

    public VendorRunnable(int vendorId, TicketPool ticketPool, int ticketReleaseRate, AtomicBoolean isRunning) {
        super(vendorId, ticketPool, ticketReleaseRate, isRunning);
    }

    // This method performs the action of adding a ticket to the ticket pool if the maximum capacity has not been reached
    // It increments the number of tickets in the ticket pool using the vendor ID
    @Override
    protected void performAction() {
        if (!ticketPool.isMaxCapacityReached()) {
            ticketPool.addTicket(id);
        }
    }

    // This method checks if the vendor should terminate
    // The vendor should terminate if the ticket pool is at maximum capacity
    @Override
    protected boolean shouldTerminate() {
        return ticketPool.isMaxCapacityReached();
    }

    // This method returns the user type as "Vendor" for logging purposes
    @Override
    protected String getUserType() {
        return "Vendor";
    }
}