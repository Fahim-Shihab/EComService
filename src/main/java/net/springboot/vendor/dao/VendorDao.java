package net.springboot.vendor.dao;

import net.springboot.common.base.ServiceResponse;
import net.springboot.vendor.payload.GetVendorsRequest;
import net.springboot.vendor.payload.GetVendorsResponse;
import net.springboot.vendor.payload.SaveVendorRequest;

public interface VendorDao {
    ServiceResponse SaveVendor(SaveVendorRequest request);
    GetVendorsResponse GetVendor(GetVendorsRequest request);
}
