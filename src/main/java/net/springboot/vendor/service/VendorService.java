package net.springboot.vendor.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.employee.model.Employee;
import net.springboot.employee.payload.SaveEmployeeRequest;
import net.springboot.employee.payload.SaveEmployeeResponse;
import net.springboot.lookup.repository.BaseRepository;
import net.springboot.security.model.LoggedInUser;
import net.springboot.vendor.model.Vendor;
import net.springboot.vendor.payload.SaveVendorRequest;
import net.springboot.vendor.repository.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorService.class);

    public VendorService(VendorRepository vendorRepository)
    {
        this.vendorRepository = vendorRepository;
    }

    public ServiceResponse SaveVendor(SaveVendorRequest request) {

        if (!Utils.isOk(request.getName())){
            return new ServiceResponse(false,"Vendor name is required");
        }

        return vendorRepository.SaveVendor(request);
    }
}
