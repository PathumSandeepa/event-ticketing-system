package com.backend.ticketing_system.cli;

import java.util.concurrent.atomic.AtomicBoolean;

public class VendorRunnable extends UserRunnable {

    public VendorRunnable(int vendorId, TicketPool ticketPool, int ticketReleaseRate, AtomicBoolean isRunning) {
        super(vendorId, ticketPool, ticketReleaseRate, isRunning);
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