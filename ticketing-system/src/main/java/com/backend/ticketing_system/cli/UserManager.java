package com.backend.ticketing_system.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;

public class UserManager {
    private List<Vendor> vendors;
    private List<Customer> customers;

    public UserManager() {
        loadUsers();
    }

    private void loadUsers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            UsersData usersData = mapper.readValue(new File("src/main/resources/config/users.json"), UsersData.class);
            this.vendors = usersData.getVendors();
            this.customers = usersData.getCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}

class UsersData {
    private List<Vendor> vendors;
    private List<Customer> customers;

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}