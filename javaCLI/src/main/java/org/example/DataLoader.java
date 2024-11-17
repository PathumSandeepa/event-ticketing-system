package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class DataLoader {
    private static final String DATA_FILE = "src/main/java/org/example/person-data.json";

    public static List<Vendor> loadVendors() {
        PersonData data = loadData();
        return data != null ? data.getVendors() : Collections.emptyList();
    }

    public static List<Customer> loadCustomers() {
        PersonData data = loadData();
        return data != null ? data.getCustomers() : Collections.emptyList();
    }

    private static PersonData loadData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(DATA_FILE);
            return mapper.readValue(file, PersonData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}