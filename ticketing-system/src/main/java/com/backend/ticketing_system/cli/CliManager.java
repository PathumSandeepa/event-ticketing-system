package com.backend.ticketing_system.cli;

import java.util.List;
import java.util.Scanner;

public class CliManager {
    private Configuration configuration;

    public static void main(String[] args) {
        CliManager cliManager = new CliManager();
        cliManager.start();
    }

    public void start() {
        configuration = new Configuration();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Total Tickets: ");
        configuration.setTotalTickets(scanner.nextInt());

        System.out.print("Enter Ticket Release Rate (ms): ");
        configuration.setTicketReleaseRate(scanner.nextInt());

        System.out.print("Enter Customer Retrieval Rate (ms): ");
        configuration.setCustomerRetrievalRate(scanner.nextInt());

        System.out.print("Enter Max Ticket Capacity: ");
        configuration.setMaxTicketCapacity(scanner.nextInt());

        Configuration.saveConfigToFile(configuration);

        UserManager userManager = new UserManager();
        List<Vendor> vendors = userManager.getVendors();
        List<Customer> customers = userManager.getCustomers();

        TicketPool ticketPool = new TicketPool(configuration);
        new Thread(ticketPool).start();

        for (Vendor vendor : vendors) {
            new Thread(() -> {
                while (true) {
                    vendor.releaseTickets(ticketPool);
                    try {
                        Thread.sleep(configuration.getTicketReleaseRate());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (Customer customer : customers) {
            new Thread(() -> {
                while (true) {
                    customer.purchaseTicket(ticketPool);
                    try {
                        Thread.sleep(configuration.getCustomerRetrievalRate());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}