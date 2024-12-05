
package com.example.runnable;

import com.example.pool.TicketPool;

public class CustomerRunnable implements Runnable {
    private String customerName;
    private TicketPool ticketPool;
    private int customerRetrievalRate;

    public CustomerRunnable(String customerName, TicketPool ticketPool, int customerRetrievalRate) {
        this.customerName = customerName;
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        while (!ticketPool.isEmpty()) {
            ticketPool.removeTickets(1, customerName, Thread.currentThread().getId());
            try {
                Thread.sleep(customerRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}