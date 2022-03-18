package net.springboot.vendor.service;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.employee.model.Employee;
import net.springboot.employee.payload.SaveEmployeeRequest;
import net.springboot.employee.payload.SaveEmployeeResponse;
import net.springboot.lookup.repository.BaseRepository;
import net.springboot.vendor.model.Vendor;
import net.springboot.vendor.payload.SaveVendorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VendorService {

    private final BaseRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorService.class);

    public VendorService(BaseRepository baseRepository) {
        this.repository = baseRepository;
    }

    public ServiceResponse SaveVendor(SaveVendorRequest request) {
        try {

            boolean isUpdate = false;
            Vendor vendor = new Vendor();

            if (Utils.isOk(request.getId())) {
                String sql = "SELECT * FROM vendor WHERE id=:vendorId ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("vendorId", request.getId());
                vendor = repository.findSingleResultByNativeQuery(sql, Vendor.class, params);

                if (vendor != null) {
                    isUpdate = true;
                    vendor.setId(request.getId());
                } else {
                    vendor = new Vendor();
                }
            }

            vendor.setName(request.getName());
            vendor.setStatus(request.getStatus().getCode());
            vendor.setVendorContactInfos(request.getVendorContactInfos());

            if (!isUpdate) {
                repository.persist(vendor);
            } else {
                repository.merge(vendor);
            }
        } catch (Throwable t) {
            LOGGER.error("VENDOR CREATION ERROR:", t);
            return new ServiceResponse(false, "Internal server error.Please contact with admin");
        }
        return new ServiceResponse(true, "Vendor has been registered successfully");
    }
}
