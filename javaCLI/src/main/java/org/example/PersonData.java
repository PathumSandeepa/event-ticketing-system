package org.example;

import java.util.List;

public class PersonData {
    private List<Vendor> vendors;
    private List<Customer> customers;

    public PersonData() {}

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