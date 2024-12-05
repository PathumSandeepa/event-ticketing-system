
package com.example.pool;

public class TicketPool {
    private int ticketsAvailable;
    private int maxTicketCapacity;
    private int totalTicketsAdded = 0;
    private boolean maxCapacityReached = false;

    public TicketPool(int ticketsAvailable, int maxTicketCapacity) {
        this.ticketsAvailable = ticketsAvailable;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public synchronized void addTickets(int count, String vendorName, long threadId) {
        if (maxCapacityReached) {
            System.out.println("Max ticket capacity is reached and vendors stop adding tickets.");
            return;
        }
        if (ticketsAvailable + count > maxTicketCapacity) {
            count = maxTicketCapacity - ticketsAvailable;
            maxCapacityReached = true;
        }
        ticketsAvailable += count;
        totalTicketsAdded += count;
        System.out.println(vendorName + " vendor added " + count + " tickets using " + threadId + " thread");
        if (ticketsAvailable >= maxTicketCapacity) {
            System.out.println("Ticket pool is full, current pool has " + ticketsAvailable);
            notifyAll();
        }
    }

    public synchronized void removeTickets(int count, String customerName, long threadId) {
        while (ticketsAvailable < count) {
            if (ticketsAvailable == 0) {
                System.out.println("Pool is empty. current pool has 0");
                return;
            }
            count = ticketsAvailable;
        }
        ticketsAvailable -= count;
        System.out.println(customerName + " purchase " + count + " tickets using " + threadId + " thread");
        notifyAll();
    }

    public synchronized boolean isFull() {
        return ticketsAvailable >= maxTicketCapacity || maxCapacityReached;
    }

    public synchronized boolean isEmpty() {
        return ticketsAvailable == 0;
    }

    public synchronized int getTicketsAvailable() {
        return ticketsAvailable;
    }
}