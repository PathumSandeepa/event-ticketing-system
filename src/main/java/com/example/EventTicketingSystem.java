
package com.example;

import com.example.model.Config;
import com.example.pool.TicketPool;
import com.example.runnable.CustomerRunnable;
import com.example.runnable.VendorRunnable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EventTicketingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get configuration parameters from user
        System.out.print("Enter Max Ticket Capacity: ");
        int maxTicketCapacity = scanner.nextInt();

        System.out.print("Enter Total Tickets: ");
        int totalTickets = scanner.nextInt();
        while (totalTickets > maxTicketCapacity) {
            System.out.println("Total Tickets must be less than or equal to Max Ticket Capacity.");
            System.out.print("Enter Total Tickets: ");
            totalTickets = scanner.nextInt();
        }

        System.out.print("Enter Ticket Release Rate (seconds): ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.print("Enter Customer Retrieval Rate (seconds): ");
        int customerRetrievalRate = scanner.nextInt();

        // Get vendor and customer counts
        System.out.print("Enter Vendor Count: ");
        int vendorCount = scanner.nextInt();

        System.out.print("Enter Customer Count: ");
        int customerCount = scanner.nextInt();

        // Save configuration to JSON file
        Config config = new Config(maxTicketCapacity, totalTickets, ticketReleaseRate, customerRetrievalRate);
        ObjectMapper mapper = new ObjectMapper();
        String configFilePath = "src/main/resources/system-config.json";
        try {
            mapper.writeValue(new File(configFilePath), config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ask to start the ticket system
        System.out.print("Ticket system start? (yes/no): ");
        String start = scanner.next();
        if (!start.equalsIgnoreCase("yes")) {
            System.out.println("Program terminated.");
            return;
        }

        // Initialize the ticket pool
        TicketPool ticketPool = new TicketPool(totalTickets, maxTicketCapacity);

        // Start vendor threads
        for (int i = 1; i <= vendorCount; i++) {
            Thread vendorThread = new Thread(new VendorRunnable("V" + i, ticketPool, ticketReleaseRate));
            vendorThread.start();
        }

        // Start customer threads
        for (int i = 1; i <= customerCount; i++) {
            Thread customerThread = new Thread(new CustomerRunnable("C" + i, ticketPool, customerRetrievalRate));
            customerThread.start();
        }
    }
}