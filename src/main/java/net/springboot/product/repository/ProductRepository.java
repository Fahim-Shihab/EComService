package net.springboot.product.repository;

import net.springboot.common.base.Defs;
import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.SearchUtil;
import net.springboot.common.util.Utils;
import net.springboot.lookup.repository.BaseRepository;
import net.springboot.product.model.ProductType;
import net.springboot.product.payload.GetProductTypeRequest;
import net.springboot.product.payload.GetProductTypeResponse;
import net.springboot.product.payload.ProductTypeDto;
import net.springboot.product.payload.SaveProductTypeRequest;
import net.springboot.security.model.LoggedInUser;
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
public class ProductRepository {

    @PersistenceContext
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepository.class);

    private final BaseRepository repository;

    public ProductRepository(BaseRepository repository){
        this.repository = repository;
    }

    @Transactional
    public ServiceResponse SaveProductType(SaveProductTypeRequest request){

        try {

            boolean isUpdate = false;
            ProductType productType = new ProductType();

            if (Utils.isOk(request.getId())) {
                String sql = "SELECT * FROM product_type WHERE id=:typeId ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("typeId", request.getId());
                productType = repository.findSingleResultByNativeQuery(sql, ProductType.class, params);

                if (productType != null) {
                    isUpdate = true;
                    productType.setId(request.getId());
                } else {
                    productType = new ProductType();
                }
            }

            productType.setCategory(request.getCategory());
            productType.setSubCategory(request.getSubCategory());
            productType.setStatus(request.getStatus().getCode());

            if(!isUpdate) {
                repository.persist(productType);
            }
            else if(isUpdate) {
                repository.merge(productType);
            }
        } catch (Throwable t) {
            LOGGER.error("PRODUCT TYPE SAVE ERROR:", t);
            return new ServiceResponse(false, "Internal server error. Please contact with admin");
        }
        return new ServiceResponse(true, "PRODUCT TYPE has been saved successfully");
    }

    @Transactional
    public GetProductTypeResponse GetProductType(GetProductTypeRequest request)
    {
        SearchUtil util = new SearchUtil();

        try{

            GetProductTypeResponse response = new GetProductTypeResponse();

            String sql = "SELECT * FROM product_type WHERE 1=1 ";
            String sqlCount = "SELECT COUNT(*) FROM product_type WHERE 1=1 ";
            Map<String, Object> params = new HashMap<>();

            String whereClause = "";

            if (Utils.isOk(request.getId())) {
                whereClause += " AND id = '"+request.getId()+"'";
            }
            if (Utils.isOk(request.getCategory())){
                whereClause += " AND Upper(category) LIKE '%"+request.getCategory().toUpperCase()+"%'";
            }
            if (Utils.isOk(request.getSubCategory())){
                whereClause += " AND Upper(sub_category) LIKE '%"+request.getSubCategory().toUpperCase()+"%'";
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
                return new GetProductTypeResponse("Could not find product type");
            }

            Query q = em.createNativeQuery(sql, ProductType.class);
            q.setFirstResult(request.getPage()*request.getSize());
            q.setMaxResults(request.getSize());
            q = util.setQueryParameter(q,request);

            List<ProductType> entitylist = q.getResultList();

            if (entitylist != null) {

                List<ProductTypeDto> list = new ArrayList<>();
                entitylist.forEach(productType -> {
                    ProductTypeDto obj = new ProductTypeDto();
                    obj.setId(productType.getId());
                    obj.setCategory(productType.getCategory());
                    obj.setSubCategory(productType.getSubCategory());
                    obj.setStatus(productType.getStatus());

                    list.add(obj);
                });
                response.setProductTypeList(list);
                response.setTotal(count);

                return response;
            }

        }catch (Throwable t) {
            LOGGER.error("Product Type fetch ERROR:", t);
            return new GetProductTypeResponse("Internal server error. Please contact with admin");
        }

        return new GetProductTypeResponse();
    }

}
