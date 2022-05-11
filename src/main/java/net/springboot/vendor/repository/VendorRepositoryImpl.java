package net.springboot.vendor.repository;

import net.springboot.common.base.Defs;
import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.SearchUtil;
import net.springboot.common.util.Utils;
import net.springboot.common.repository.BaseRepository;
import net.springboot.security.model.LoggedInUser;
import net.springboot.vendor.dao.VendorDaoImpl;
import net.springboot.vendor.model.Vendor;
import net.springboot.vendor.payload.GetVendorsRequest;
import net.springboot.vendor.payload.GetVendorsResponse;
import net.springboot.vendor.payload.SaveVendorRequest;
import net.springboot.vendor.payload.VendorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VendorRepositoryImpl implements VendorRepository {

    @Autowired
    VendorDaoImpl vendorDaoImpl;

    @Override
    public ServiceResponse SaveVendor(SaveVendorRequest request){

        ServiceResponse response = vendorDaoImpl.SaveVendor(request);

        return response;
    }

    @Override
    public GetVendorsResponse GetVendor(GetVendorsRequest request)
    {
        GetVendorsResponse response = vendorDaoImpl.GetVendor(request);

        return response;
    }
}
