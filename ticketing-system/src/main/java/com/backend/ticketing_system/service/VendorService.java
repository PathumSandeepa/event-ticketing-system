package com.backend.ticketing_system.service;

import com.backend.ticketing_system.dto.VendorRegistrationDto;
import com.backend.ticketing_system.model.Vendor;

public interface VendorService {
    Vendor registerVendor(VendorRegistrationDto registrationDto);
    Vendor authenticateVendor(String email, String password);
}