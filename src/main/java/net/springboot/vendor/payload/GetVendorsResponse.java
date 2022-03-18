package net.springboot.vendor.payload;

import net.springboot.common.base.ServiceResponse;

import java.util.List;

public class GetVendorsResponse extends ServiceResponse {
    private List<SaveVendorRequest> vendorList;
}
