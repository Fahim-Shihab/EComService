package net.springboot.vendor.repository;

import net.springboot.common.base.ServiceResponse;
import net.springboot.vendor.payload.GetVendorsRequest;
import net.springboot.vendor.payload.GetVendorsResponse;
import net.springboot.vendor.payload.SaveVendorRequest;

public interface VendorRepository {
    ServiceResponse SaveVendor(SaveVendorRequest request);
    GetVendorsResponse GetVendor(GetVendorsRequest request);
}
