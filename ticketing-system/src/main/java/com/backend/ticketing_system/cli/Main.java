package com.backend.ticketing_system.cli;

import com.backend.ticketing_system.model.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static AtomicBoolean isRunning = new AtomicBoolean(true);
    private static List<Thread> mainThreads;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int maxTicketCapacity = 0;
        while (true) {
            try {
                System.out.print("Enter Max Ticket Capacity: ");
                maxTicketCapacity = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (maxTicketCapacity <= 0) {
                    System.out.println("Max Ticket Capacity must be positive.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next(); // Clear invalid input
            }
        }

        int totalTickets = 0;
        while (true) {
            try {
                System.out.print("Enter Total Tickets: ");
                totalTickets = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (totalTickets <= 0 || totalTickets >= maxTicketCapacity) {
                    System.out.println("Total Tickets must be positive and less than Max Ticket Capacity.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer less than Max Ticket Capacity.");
                scanner.next(); // Clear invalid input
            }
        }

        int ticketReleaseRate = 0;
        while (true) {
            try {
                System.out.print("Enter Ticket Release Rate (seconds): ");
                ticketReleaseRate = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (ticketReleaseRate <= 0) {
                    System.out.println("Ticket Release Rate must be positive.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next();
            }
        }

        int customerRetrievalRate = 0;
        while (true) {
            try {
                System.out.print("Enter Customer Retrieval Rate (seconds): ");
                customerRetrievalRate = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (customerRetrievalRate <= 0) {
                    System.out.println("Customer Retrieval Rate must be positive.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next();
            }
        }

        int vendorCount = 0;
        while (true) {
            try {
                System.out.print("Enter Vendor Count: ");
                vendorCount = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (vendorCount <= 0) {
                    System.out.println("Vendor Count must be positive.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next();
            }
        }

        int customerCount = 0;
        while (true) {
            try {
                System.out.print("Enter Customer Count: ");
                customerCount = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (customerCount <= 0) {
                    System.out.println("Customer Count must be positive.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next();
            }
        }

        String start = "";
        while (true) {
            System.out.print("Ticket system start? (yes/no): ");
            start = scanner.nextLine();
            if (start.equalsIgnoreCase("yes") || start.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        if (!start.equalsIgnoreCase("yes")) {
            Logger.log("Program terminated.");
            scanner.close();
            return;
        }

        // Save configuration to JSON file
        saveConfig(maxTicketCapacity, totalTickets, ticketReleaseRate, customerRetrievalRate);

        Configuration configuration = new Configuration();
        configuration.setTotalTickets(totalTickets);
        configuration.setTicketReleaseRate(ticketReleaseRate);
        configuration.setCustomerRetrievalRate(customerRetrievalRate);
        configuration.setMaxTicketCapacity(maxTicketCapacity);
        configuration.setVendorCount(vendorCount);
        configuration.setCustomerCount(customerCount);

        // Start the ticket system with the user-provided configuration
        startTicketSystem(configuration);

        // Thread to listen for 'no' input to stop the system
        Thread stopThread = new Thread(() -> {
            Scanner inputScanner = new Scanner(System.in);
            while (isRunning.get()) {
                System.out.print("Enter 'no' to stop the ticket system: ");
                System.out.print("");
                String input = inputScanner.nextLine();
                if (input.equalsIgnoreCase("no")) {
                    Logger.log("Stopping the ticket system...");
                    isRunning.set(false);
                    break;
                }
            }
            inputScanner.close();
        });
        stopThread.start();

        // Wait for the stopThread to finish
        try {
            stopThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait for all threads to finish
        for (Thread thread : mainThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        scanner.close();
        Logger.log("Program terminated.");
    }

    private static void saveConfig(int maxTicketCapacity, int totalTickets, int ticketReleaseRate, int customerRetrievalRate) {
        Config config = new Config(maxTicketCapacity, totalTickets, ticketReleaseRate, customerRetrievalRate);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("system-config.json"), config);
            Logger.log("Configuration saved to system-config.json");
        } catch (IOException e) {
            Logger.log("Failed to save configuration: " + e.getMessage());
        }
    }

    public static void startTicketSystem(Configuration configuration) {
        // Initialize the ticket pool
        TicketPool ticketPool = new TicketPool(configuration.getTotalTickets(), configuration.getMaxTicketCapacity());

        List<Thread> threads = new ArrayList<>();

        // Start vendor threads
        for (int i = 1; i <= configuration.getVendorCount(); i++) {
            VendorRunnable vendor = new VendorRunnable(i, ticketPool, configuration.getTicketReleaseRate(), isRunning);
            Thread vendorThread = new Thread(vendor, "Vendor-" + i);
            vendorThread.start();
            threads.add(vendorThread);
        }

        // Start customer threads
        for (int i = 1; i <= configuration.getCustomerCount(); i++) {
            CustomerRunnable customer = new CustomerRunnable(i, ticketPool, configuration.getCustomerRetrievalRate(), isRunning);
            Thread customerThread = new Thread(customer, "Customer-" + i);
            customerThread.start();
            threads.add(customerThread);
        }

        mainThreads = threads;
    }
}