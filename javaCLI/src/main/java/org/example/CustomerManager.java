package org.example;

import java.util.List;
import java.util.Random;

public class CustomerManager implements Manager {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final List<Customer> customers;

    public CustomerManager(TicketPool ticketPool, int customerRetrievalRate, List<Customer> customers) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customers = customers;
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
        Random random = new Random();
        while (!ticketPool.isMaxCapacityReached() || !ticketPool.isPoolEmpty()) {
            synchronized (ticketPool) {
                while (!ticketPool.isPoolFull() && !ticketPool.isMaxCapacityReached()) {
                    ticketPool.wait();
                }

                while (ticketPool.getTicketCount() > 0) {
                    for (Customer customer : customers) {
                        if (ticketPool.getTicketCount() <= 0) {
                            break;
                        }

                        int maxTicketsPerCustomer = 3;
                        int ticketsToBuy = random.nextInt(maxTicketsPerCustomer) + 1;
                        ticketsToBuy = Math.min(ticketsToBuy, ticketPool.getTicketCount());

                        ticketPool.purchaseTickets(customer.getName(), ticketsToBuy);
                        Thread.sleep(customerRetrievalRate);

                        if (ticketPool.getTicketCount() <= 0) {
                            break;
                        }
                    }
                }

                Logger.log("Ticket pool is empty, pool count is: " + ticketPool.getTicketCount());
                ticketPool.notifyAll();
            }
        }
        Logger.log("Max ticket capacity reached. Customers stopped purchasing tickets.");
    }
}