package com.backend.ticketing_system.cli;

public class TicketPool {
    private final int poolCapacity;
    private final int maxTicketCapacity;
    private int currentTickets = 0;
    private int ticketsAdded = 0;
    private boolean isMaxCapacityReached = false;
    private boolean hasPrintedSoldOutMessage = false;

    public TicketPool(int poolCapacity, int maxTicketCapacity) {
        this.poolCapacity = poolCapacity;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public synchronized void addTicket(int vendorId) {
        while (currentTickets >= poolCapacity) {
            if (ticketsAdded >= maxTicketCapacity) {
                isMaxCapacityReached = true;
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

        if (ticketsAdded >= maxTicketCapacity) {
            isMaxCapacityReached = true;
            Logger.log("Max ticket capacity is reached and vendors stop adding tickets.");
            notifyAll();
            return;
        }

        currentTickets++;
        ticketsAdded++;
        Logger.log("Vendor " + vendorId + " added 1 ticket using #" + Thread.currentThread().getName() + " Thread.");
        notifyAll();
    }

    public synchronized void removeTicket(int customerId) {
        while (currentTickets <= 0) {
            if (isMaxCapacityReached && !hasPrintedSoldOutMessage) {
                hasPrintedSoldOutMessage = true;
                Logger.log("Max ticket capacity is reached. All tickets are sold out.");
                notifyAll();
                return;
            }
            try {
                Logger.log("Customer " + customerId + " is waiting as pool is empty.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        currentTickets--;
        Logger.log("Customer " + customerId + " purchased 1 ticket using #" + Thread.currentThread().getName()  + " Thread.");
        notifyAll();
    }

    public synchronized boolean isMaxCapacityReached() {
        return isMaxCapacityReached;
    }

    public synchronized int getCurrentTickets() {
        return currentTickets;
    }

    public int getPoolCapacity() {
        return poolCapacity;
    }
}