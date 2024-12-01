// Main.java
package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int maxTicketCapacity = 0;
        while (true) {
            try {
                System.out.print("Enter Max Ticket Capacity: ");
                maxTicketCapacity = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline
                if (maxTicketCapacity <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer for Max Ticket Capacity.");
                scanner.next(); // Clear invalid input
            }
        }

        int totalTickets = 0;
        while (true) {
            try {
                System.out.print("Enter Total Tickets: ");
                totalTickets = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline
                if (totalTickets <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer.");
                    continue;
                }
                if (totalTickets > maxTicketCapacity) {
                    System.out.println("Total Tickets cannot exceed Max Ticket Capacity.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer for Total Tickets.");
                scanner.next(); // Clear invalid input
            }
        }

        int ticketReleaseRate = 0;
        while (true) {
            try {
                System.out.print("Enter Ticket Release Rate (seconds): ");
                ticketReleaseRate = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline
                if (ticketReleaseRate <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer for Ticket Release Rate.");
                scanner.next(); // Clear invalid input
            }
        }

        int customerRetrievalRate = 0;
        while (true) {
            try {
                System.out.print("Enter Customer Retrieval Rate (seconds): ");
                customerRetrievalRate = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline
                if (customerRetrievalRate <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer for Customer Retrieval Rate.");
                scanner.next(); // Clear invalid input
            }
        }

        int vendorCount = 0;
        while (true) {
            try {
                System.out.print("Enter number of Vendors: ");
                vendorCount = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline
                if (vendorCount <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer for number of Vendors.");
                scanner.next(); // Clear invalid input
            }
        }

        int customerCount = 0;
        while (true) {
            try {
                System.out.print("Enter number of Customers: ");
                customerCount = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline
                if (customerCount <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer for number of Customers.");
                scanner.next(); // Clear invalid input
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

        // Initialize the ticket pool
        TicketPool ticketPool = new TicketPool(totalTickets, maxTicketCapacity);

        // Start vendor threads
        for (int i = 1; i <= vendorCount; i++) {
            VendorRunnable vendorRunnable = new VendorRunnable(i, ticketPool, ticketReleaseRate);
            Thread vendorThread = new Thread(vendorRunnable, "Vendor-" + i);
            vendorThread.start();
        }

        // Start customer threads
        for (int i = 1; i <= customerCount; i++) {
            CustomerRunnable customerRunnable = new CustomerRunnable(i, ticketPool, customerRetrievalRate);
            Thread customerThread = new Thread(customerRunnable, "Customer-" + i);
            customerThread.start();
        }

        scanner.close();
    }

    private static void saveConfig(int maxTicketCapacity, int totalTickets, int ticketReleaseRate,
            int customerRetrievalRate) {
        Config config = new Config(maxTicketCapacity, totalTickets, ticketReleaseRate, customerRetrievalRate);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("system-config.json"), config);
            Logger.log("Configuration saved to system-config.json");
        } catch (IOException e) {
            Logger.log("Failed to save configuration: " + e.getMessage());
        }
    }
}