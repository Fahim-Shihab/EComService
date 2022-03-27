package net.springboot.vendor.controller;

import net.springboot.common.base.ServiceResponse;
import net.springboot.vendor.payload.GetVendorsRequest;
import net.springboot.vendor.payload.GetVendorsResponse;
import net.springboot.vendor.payload.SaveVendorRequest;
import net.springboot.vendor.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService)
    {
        this.vendorService = vendorService;
    }

    @PostMapping("/saveVendor")
    public @ResponseBody
    ServiceResponse SaveVendor(@RequestBody SaveVendorRequest request)
    {
        return vendorService.SaveVendor(request);
    }

    @PostMapping("/getVendor")
    public @ResponseBody
    GetVendorsResponse GetVendor(@RequestBody GetVendorsRequest request){
        return vendorService.GetVendor(request);
    }

}
