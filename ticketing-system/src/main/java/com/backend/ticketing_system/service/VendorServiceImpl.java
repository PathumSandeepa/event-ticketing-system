package com.backend.ticketing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.ticketing_system.dto.VendorRegistrationDto;
import com.backend.ticketing_system.model.Vendor;
import com.backend.ticketing_system.repository.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Vendor registerVendor(VendorRegistrationDto registrationDto) {
        Vendor vendor = new Vendor();
        vendor.setName(registrationDto.getName());
        vendor.setEmail(registrationDto.getEmail());
        vendor.setPhone(registrationDto.getPhone());
        vendor.setAddress(registrationDto.getAddress());
        vendor.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor authenticateVendor(String email, String password) {
        Vendor vendor = vendorRepository.findByEmail(email);
        if (vendor != null && passwordEncoder.matches(password, vendor.getPassword())) {
            return vendor;
        }
        return null;
    }
}