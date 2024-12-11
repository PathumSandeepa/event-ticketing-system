package com.backend.ticketing_system.cli;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class UserRunnable implements Runnable {
    protected final int id;
    protected final TicketPool ticketPool;
    protected final int activityRate;
    protected final AtomicBoolean isRunning;

    public UserRunnable(int id, TicketPool ticketPool, int activityRate, AtomicBoolean isRunning) {
        this.id = id;
        this.ticketPool = ticketPool;
        this.activityRate = activityRate;
        this.isRunning = isRunning;
    }

    protected abstract void performAction();

    protected abstract boolean shouldTerminate();

    protected abstract String getUserType();

    @Override
    public void run() {
        while (isRunning.get()) {
            if (shouldTerminate()) {
                break;
            }
            synchronized (ticketPool) {
                performAction();
                ticketPool.notifyAll();
            }
            try {
                Thread.sleep(activityRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        Logger.log(getUserType() + " " + id + " has stopped.");
    }
}