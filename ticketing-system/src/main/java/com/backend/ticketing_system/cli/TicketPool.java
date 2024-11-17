package com.backend.ticketing_system.cli;

public class TicketPool implements Runnable {
    private final Configuration config;
    private int availableTickets;

    public TicketPool(Configuration config) {
        this.config = config;
        this.availableTickets = 0;
    }

    @Override
    public synchronized void run() {
        final int[] ticketsToRelease = {config.getTotalTickets()};
        int maxCapacity = config.getMaxTicketCapacity();
        int releaseRate = config.getTicketReleaseRate();
        int retrievalRate = config.getCustomerRetrievalRate();

        Thread releaseThread = new Thread(() -> {
            while (ticketsToRelease[0] > 0) {
                try {
                    Thread.sleep(releaseRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int ticketsToAdd = Math.min(ticketsToRelease[0], maxCapacity - availableTickets);
                releaseTickets(ticketsToAdd);
                ticketsToRelease[0] -= ticketsToAdd;
                System.out.println(ticketsToAdd + " tickets released. Remaining tickets: " + ticketsToRelease[0]);
            }
        });

        Thread retrievalThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(retrievalRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retrieveTicket()) {
                    System.out.println("Ticket purchased by a customer.");
                } else {
                    System.out.println("No tickets available for purchase.");
                }
            }
        });

        releaseThread.start();
        retrievalThread.start();

        try {
            releaseThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tickets have been released.");
    }

    public synchronized void releaseTickets(int count) {
        if (availableTickets + count <= config.getMaxTicketCapacity()) {
            availableTickets += count;
            notifyAll();
        }
    }

    public synchronized boolean retrieveTicket() {
        if (availableTickets > 0) {
            availableTickets--;
            return true;
        }
        return false;
    }
}