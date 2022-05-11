package net.springboot.vendor.dao;

import net.springboot.common.base.Defs;
import net.springboot.common.base.ServiceResponse;
import net.springboot.common.repository.BaseRepository;
import net.springboot.common.util.SearchUtil;
import net.springboot.common.util.Utils;
import net.springboot.security.model.LoggedInUser;
import net.springboot.vendor.model.Vendor;
import net.springboot.vendor.payload.GetVendorsRequest;
import net.springboot.vendor.payload.GetVendorsResponse;
import net.springboot.vendor.payload.SaveVendorRequest;
import net.springboot.vendor.payload.VendorDto;
import net.springboot.vendor.repository.VendorRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class VendorDaoImpl implements VendorDao {

    @PersistenceContext
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorRepositoryImpl.class);

    private final BaseRepository repository;

    public VendorDaoImpl(BaseRepository repository){
        this.repository = repository;
    }

    @Transactional
    public ServiceResponse SaveVendor(SaveVendorRequest request){
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

    @Transactional
    public GetVendorsResponse GetVendor(GetVendorsRequest request){
        SearchUtil util = new SearchUtil();

        try{

            GetVendorsResponse response = new GetVendorsResponse();

            String sql = "SELECT * FROM vendor WHERE 1=1 ";
            String sqlCount = "SELECT COUNT(*) FROM vendor WHERE 1=1 ";
            Map<String, Object> params = new HashMap<>();

            String whereClause = "";

            if (Utils.isOk(request.getId())) {
                whereClause += " AND id = '"+request.getId()+"'";
            }
            if (Utils.isOk(request.getName())){
                whereClause += " AND Upper(name) LIKE '%"+request.getName().toUpperCase()+"%'";
            }
            if (Utils.isOk(request.getStatus())) {
                whereClause += " AND status = '"+request.getStatus().getCode()+"'";
            }

            if (request.getPage() - 1 <= 0) {
                request.setPage(0);
            } else {
                request.setPage(request.getPage() - 1);
            }
            if (request.getSize() == 0) {
                request.setSize(Defs.DEFAULT_LIMIT);
            }

            sql += whereClause;
            sqlCount += whereClause;

            Query qCount = em.createNativeQuery(sqlCount);

            BigInteger count = (BigInteger) qCount.getSingleResult();

            if (count.longValueExact() <= 0 || (count.longValueExact() < (request.getPage()*request.getSize()))){
                return new GetVendorsResponse("Could not find vendor");
            }

            Query q = em.createNativeQuery(sql, Vendor.class);
            q.setFirstResult(request.getPage()*request.getSize());
            q.setMaxResults(request.getSize());
            q = util.setQueryParameter(q,request);

            List<Vendor> entitylist = q.getResultList();

            if (entitylist != null) {

                List<VendorDto> list = new ArrayList<>();
                entitylist.forEach(userInfo -> {
                    VendorDto obj = new VendorDto();
                    obj.setId(userInfo.getId());
                    obj.setName(userInfo.getName());
                    obj.setStatus(userInfo.getStatus());
                    obj.setVendorContactInfos(userInfo.getVendorContactInfos());

                    list.add(obj);
                });
                response.setVendorList(list);
                response.setTotal(count);

                return response;
            }

        }catch (Throwable t) {
            LOGGER.error("Vendor fetch ERROR:", t);
            return new GetVendorsResponse("Internal server error. Please contact with admin");
        }

        return new GetVendorsResponse();
    }
}
