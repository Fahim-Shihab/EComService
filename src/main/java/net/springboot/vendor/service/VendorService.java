package net.springboot.vendor.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.vendor.payload.GetVendorsRequest;
import net.springboot.vendor.payload.GetVendorsResponse;
import net.springboot.vendor.payload.SaveVendorRequest;
import net.springboot.vendor.repository.VendorRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    private final VendorRepositoryImpl vendorRepository;

    public VendorService(VendorRepositoryImpl vendorRepository)
    {
        this.vendorRepository = vendorRepository;
    }

    public ServiceResponse SaveVendor(SaveVendorRequest request) {

        if (!Utils.isOk(request.getName())){
            return new ServiceResponse(false,"Vendor name is required");
        }

        return vendorRepository.SaveVendor(request);
    }

    public GetVendorsResponse GetVendor(GetVendorsRequest request){
        return vendorRepository.GetVendor(request);
    }
}
