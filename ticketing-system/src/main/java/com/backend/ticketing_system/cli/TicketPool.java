package com.backend.ticketing_system.cli;

// TicketPool class is used to manage the ticket pool and provide the functionality to add and remove tickets from the pool.
//oop concepts used: encapsulation
public class TicketPool {
    private final int poolCapacity;
    private final int maxTicketCapacity;
    private int currentTickets = 0;
    private int ticketsAdded = 0;
    private boolean isMaxCapacityReached = false;
    private boolean hasPrintedSoldOutMessage = false;

    // Constructor to initialize the pool capacity and max ticket capacity.
    public TicketPool(int poolCapacity, int maxTicketCapacity) {
        this.poolCapacity = poolCapacity;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Method to add ticket to the pool. It is synchronized to avoid
    public synchronized void addTicket(int vendorId) {
        while (currentTickets >= poolCapacity) {
            if (ticketsAdded >= maxTicketCapacity) {
                isMaxCapacityReached = true; // Set max capacity reached flag.
                notifyAll();
                return;
            }
            try {
                Logger.log("Vendor " + vendorId + " is waiting as pool is full.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        // Check if max ticket capacity is reached.
        if (ticketsAdded >= maxTicketCapacity) {
            isMaxCapacityReached = true; // Set max capacity reached flag.
            Logger.log("Max ticket capacity is reached and vendors stop adding tickets.");
            notifyAll();
            return;
        }
        // Add ticket to the pool.
        currentTickets++; // Increment the current tickets count.
        ticketsAdded++; // Increment the tickets added count.
        Logger.log("Vendor " + vendorId + " added 1 ticket using #" + Thread.currentThread().getName() + " Thread.");
        notifyAll();
    }

    // Method to remove ticket from the pool.
    // It is synchronized
    // It will wait if the pool is empty.
    public synchronized void removeTicket(int customerId) {
        while (currentTickets <= 0) {
            if (isMaxCapacityReached && !hasPrintedSoldOutMessage) {
                hasPrintedSoldOutMessage = true; // Print the sold out message only once.
                Logger.log("Max ticket capacity is reached. All tickets are sold out.");
                notifyAll(); // Notify all waiting threads.
                return;
            }
            try {
                Logger.log("Customer " + customerId + " is waiting as pool is empty.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status.
                return;
            }
        }
        // Remove ticket from the pool.
        currentTickets--;
        Logger.log("Customer " + customerId + " purchased 1 ticket using #" + Thread.currentThread().getName()  + " Thread.");
        notifyAll(); // Notify all waiting threads.
    }
    // Method to check if the max capacity is reached. It is synchronized
    public synchronized boolean isMaxCapacityReached() {
        return isMaxCapacityReached;
    }

    // Method to get the current tickets in the pool. It is synchronized
    public synchronized int getCurrentTickets() {
        return currentTickets;
    }

    // Method to get the pool capacity.
    public int getPoolCapacity() {
        return poolCapacity;
    }
}