package org.example;

public class VendorRunnable extends UserRunnable {

    public VendorRunnable(int vendorId, TicketPool ticketPool, int ticketReleaseRate) {
        super(vendorId, ticketPool, ticketReleaseRate);
    }

    @Override
    protected void performAction() {
        if (!ticketPool.isMaxCapacityReached()) {
            ticketPool.addTicket(id);
        }
    }

    @Override
    protected boolean shouldTerminate() {
        return ticketPool.isMaxCapacityReached();
    }

    @Override
    protected String getUserType() {
        return "Vendor";
    }
}