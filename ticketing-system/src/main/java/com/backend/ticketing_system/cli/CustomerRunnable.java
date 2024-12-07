package com.backend.ticketing_system.cli;

public class CustomerRunnable extends UserRunnable {

    public CustomerRunnable(int customerId, TicketPool ticketPool, int customerRetrievalRate) {
        super(customerId, ticketPool, customerRetrievalRate);
    }

    @Override
    protected void performAction() {
        if (ticketPool.getCurrentTickets() > 0) {
            ticketPool.removeTicket(id);
        }
    }

    @Override
    protected boolean shouldTerminate() {
        return ticketPool.isMaxCapacityReached() && ticketPool.getCurrentTickets() == 0;
    }

    @Override
    protected String getUserType() {
        return "Customer";
    }
}