package com.backend.ticketing_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "configuration")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Total tickets is required")
    @Min(value = 1, message = "Total tickets must be greater than 0")
    private int totalTickets;

    @NotNull(message = "Ticket release rate is required")
    @Min(value = 1, message = "Ticket release rate must be greater than 0")
    private int ticketReleaseRate;

    @NotNull(message = "Customer retrieval rate is required")
    @Min(value = 1, message = "Customer retrieval rate must be greater than 0")
    private int customerRetrievalRate;

    @NotNull(message = "Max ticket capacity is required")
    @Min(value = 1, message = "Max ticket capacity must be greater than 0")
    private int maxTicketCapacity;

    @NotNull(message = "Vendor count is required")
    @Min(value = 1, message = "Vendor count must be greater than 0")
    private int vendorCount;

    @NotNull(message = "Customer count is required")
    @Min(value = 1, message = "Customer count must be greater than 0")
    private int customerCount;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }
}