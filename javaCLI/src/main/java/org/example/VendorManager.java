package org.example;

import java.util.List;

public class VendorManager implements Manager {
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final List<Vendor> vendors;

    public VendorManager(TicketPool ticketPool, int ticketReleaseRate, List<Vendor> vendors) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendors = vendors;
    }

    @Override
    public void run() {
        try {
            manage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void manage() throws InterruptedException {
        while (!ticketPool.isMaxCapacityReached()) {
            synchronized (ticketPool) {
                while (!ticketPool.isPoolEmpty()) {
                    ticketPool.wait();
                }

                int totalTicketsToAdd = Math.min(ticketPool.getInitialTicketCount(), ticketPool.getRemainingCapacity());
                if (totalTicketsToAdd <= 0) {
                    break;
                }

                int ticketsAdded = 0;
                int vendorsCount = vendors.size();

                for (int i = 0; i < vendorsCount; i++) {
                    Vendor vendor = vendors.get(i);
                    int ticketsToAdd;

                    if (i < vendorsCount - 1) {
                        ticketsToAdd = totalTicketsToAdd / vendorsCount;
                    } else {
                        ticketsToAdd = totalTicketsToAdd - ticketsAdded;
                    }

                    if (ticketsToAdd <= 0) {
                        continue;
                    }

                    int ticketsActuallyAdded = ticketPool.addTickets(ticketsToAdd);
                    ticketsAdded += ticketsActuallyAdded;
                    System.out.println(vendor.getId() + " Vendor added " + ticketsActuallyAdded + " tickets to the pool");
                    Thread.sleep(ticketReleaseRate);

                    if (ticketPool.isPoolFull() || ticketPool.isMaxCapacityReached()) {
                        break;
                    }
                }

                System.out.println("Ticket pool is full, ticket count is: " + ticketPool.getTicketCount());
                ticketPool.notifyAll();
            }
        }
        System.out.println("Max ticket capacity reached. Vendors stopped adding tickets.");
    }
}