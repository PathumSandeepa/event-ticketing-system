package com.backend.ticketing_system.cli;

import java.util.concurrent.atomic.AtomicBoolean;

// This class represents a user thread that interacts with the ticket pool
// oop concepts used: Encapsulation, Inheritance, Polymorphism
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

    // This method performs the action of the user thread
    protected abstract void performAction();

    // This method checks if the user should terminate
    protected abstract boolean shouldTerminate();

    // This method returns the user type for logging purposes
    protected abstract String getUserType();

    // This method runs the user thread and performs the action at a certain rate
    // It also checks if the user should terminate
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