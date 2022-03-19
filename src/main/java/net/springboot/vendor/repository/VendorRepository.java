package net.springboot.vendor.repository;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.lookup.repository.BaseRepository;
import net.springboot.security.model.LoggedInUser;
import net.springboot.vendor.model.Vendor;
import net.springboot.vendor.payload.SaveVendorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Repository
public class VendorRepository {

    @PersistenceContext
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorRepository.class);

    private final BaseRepository repository;
    private final PasswordEncoder passwordEncoder;

    public VendorRepository(BaseRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ServiceResponse SaveVendor(SaveVendorRequest request){
        if (request == null){
            return new ServiceResponse(false, "Request cannot be empty");
        }

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

            Timestamp timestamp = Utils.getCurrentTimeStamp();

            LoggedInUser user = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(!isUpdate) {
                vendor.setEntryDate(timestamp);
                vendor.setEntryById(user.getId());

                repository.persist(vendor);
            }
            else if(isUpdate) {
                vendor.setUpdDate(timestamp);
                vendor.setUpdById(user.getId());

                repository.merge(vendor);
            }
        } catch (Throwable t) {
            LOGGER.error("VENDOR SAVE ERROR:", t);
            return new ServiceResponse(false, "Internal server error. Please contact with admin");
        }
        return new ServiceResponse(true, "Vendor has been saved successfully");
    }
}
