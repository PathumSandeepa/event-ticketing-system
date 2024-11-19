package org.example;

public class TicketPool {
    private int ticketCount;
    private int maxTicketCapacity;
    private final int initialTicketCount;

    public TicketPool(int totalTickets, int maxTicketCapacity) {
        this.ticketCount = 0;
        this.initialTicketCount = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public synchronized int addTickets(int ticketsToAdd) {
        int ticketsAllowed = Math.min(ticketsToAdd, maxTicketCapacity);
        ticketCount += ticketsAllowed;
        maxTicketCapacity -= ticketsAllowed;
        return ticketsAllowed;
    }

    public synchronized void purchaseTickets(String customerName, int ticketsToBuy) {
        ticketCount -= ticketsToBuy;
        Logger.log(customerName + " purchased " + ticketsToBuy + " Tickets from the pool");
    }

    public synchronized boolean isMaxCapacityReached() {
        return maxTicketCapacity <= 0;
    }

    public synchronized boolean isPoolFull() {
        return ticketCount >= initialTicketCount || maxTicketCapacity <= 0;
    }

    public synchronized boolean isPoolEmpty() {
        return ticketCount <= 0;
    }

    // Getters
    public int getTicketCount() {
        return ticketCount;
    }

    public int getInitialTicketCount() {
        return initialTicketCount;
    }

    public int getRemainingCapacity() {
        return maxTicketCapacity;
    }
}