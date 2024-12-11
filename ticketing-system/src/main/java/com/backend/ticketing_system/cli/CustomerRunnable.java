package com.backend.ticketing_system.cli;

import java.util.concurrent.atomic.AtomicBoolean;

// This class represents a customer thread that removes tickets from the ticket pool at a certain rate until the ticket pool is empty
// oop concepts used: Encapsulation, Inheritance, Polymorphism
public class CustomerRunnable extends UserRunnable {

    public CustomerRunnable(int customerId, TicketPool ticketPool, int customerRetrievalRate, AtomicBoolean isRunning) {
        super(customerId, ticketPool, customerRetrievalRate, isRunning);
    }

    // This method performs the action of removing a ticket from the ticket pool
    // It decrements the number of tickets in the ticket pool
    // The ticket is removed using the customer ID
    @Override
    protected void performAction() {
        if (ticketPool.getCurrentTickets() > 0) {
            ticketPool.removeTicket(id);
        }
    }

    // This method checks if the customer should terminate
    // The customer should terminate if the ticket pool is at maximum capacity and there are no tickets left
    @Override
    protected boolean shouldTerminate() {
        return ticketPool.isMaxCapacityReached() && ticketPool.getCurrentTickets() == 0;
    }

    // This method returns the user type as "Customer" for logging purposes
    @Override
    protected String getUserType() {
        return "Customer";
    }
}