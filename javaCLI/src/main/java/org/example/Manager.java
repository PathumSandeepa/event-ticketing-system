package org.example;

public interface Manager extends Runnable {
    void manage() throws InterruptedException;
}