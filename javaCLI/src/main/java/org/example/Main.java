package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int totalTickets;
    private static int maxTicketCapacity;
    private static int ticketReleaseRate;
    private static int customerRetrievalRate;

    public static void main(String[] args) {
        // Get user input for system configuration
        getUserInput();

        // Configuration file handling
        ObjectMapper mapper = new ObjectMapper();
        Config config = new Config(maxTicketCapacity, totalTickets, ticketReleaseRate, customerRetrievalRate);
        File configFile = new File("src/main/resources/system-config.json");

        try {
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(configFile, config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load data
        List<Vendor> vendors = DataLoader.loadVendors();
        List<Customer> customers = DataLoader.loadCustomers();

        // Initialize and start the ticketing system
        TicketPool ticketPool = new TicketPool(totalTickets, maxTicketCapacity);

        VendorManager vendorManager = new VendorManager(ticketPool, ticketReleaseRate, vendors);
        CustomerManager customerManager = new CustomerManager(ticketPool, customerRetrievalRate, customers);

        Thread vendorThread = new Thread(vendorManager);
        Thread customerThread = new Thread(customerManager);

        vendorThread.start();
        customerThread.start();
    }

    private static void getUserInput() {
        Scanner scanner = new Scanner(System.in);

        // Input validation for Total Tickets
        while (true) {
            System.out.print("Enter Total Tickets: ");
            try {
                totalTickets = scanner.nextInt();
                if (totalTickets > 0) {
                    break;
                } else {
                    System.out.println("Total Tickets must be greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer value.");
                scanner.next(); // Clear invalid input
            }
        }

        // Input validation for Max Ticket Capacity
        while (true) {
            System.out.print("Enter Max Ticket Capacity: ");
            try {
                maxTicketCapacity = scanner.nextInt();
                if (maxTicketCapacity > totalTickets) {
                    break;
                } else {
                    System.out.println("Max Ticket Capacity must be greater than Total Tickets.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer value.");
                scanner.next();
            }
        }

        // Input validation for Ticket Release Rate
        while (true) {
            System.out.print("Enter Ticket Release Rate (in milliseconds): ");
            try {
                ticketReleaseRate = scanner.nextInt();
                if (ticketReleaseRate >= 1000) {
                    break;
                } else {
                    System.out.println("Ticket Release Rate must be 1000 or higher.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer value.");
                scanner.next();
            }
        }

        // Input validation for Customer Retrieval Rate
        while (true) {
            System.out.print("Enter Customer Retrieval Rate (in milliseconds): ");
            try {
                customerRetrievalRate = scanner.nextInt();
                if (customerRetrievalRate >= 1000) {
                    break;
                } else {
                    System.out.println("Customer Retrieval Rate must be 1000 or higher.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer value.");
                scanner.next();
            }
        }

        scanner.close();
    }
}