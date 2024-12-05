
package com.example.runnable;

import com.example.pool.TicketPool;

public class VendorRunnable implements Runnable {
    private String vendorName;
    private TicketPool ticketPool;
    private int ticketReleaseRate;

    public VendorRunnable(String vendorName, TicketPool ticketPool, int ticketReleaseRate) {
        this.vendorName = vendorName;
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        while (!ticketPool.isFull()) {
            ticketPool.addTickets(1, vendorName, Thread.currentThread().getId());
            try {
                Thread.sleep(ticketReleaseRate * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}