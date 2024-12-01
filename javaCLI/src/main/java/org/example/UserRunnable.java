package org.example;

public abstract class UserRunnable implements Runnable {
    protected final int id;
    protected final TicketPool ticketPool;
    protected final int activityRate;

    public UserRunnable(int id, TicketPool ticketPool, int activityRate) {
        this.id = id;
        this.ticketPool = ticketPool;
        this.activityRate = activityRate;
    }

    protected abstract void performAction();

    protected abstract boolean shouldTerminate();

    protected abstract String getUserType();

    @Override
    public void run() {
        while (true) {
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