package net.springboot.product.dao;

import net.springboot.common.base.Defs;
import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.SearchUtil;
import net.springboot.common.util.Utils;
import net.springboot.common.repository.BaseRepository;
import net.springboot.minio.MinioService;
import net.springboot.product.model.Product;
import net.springboot.product.payload.product.GetProductRequest;
import net.springboot.product.payload.product.GetProductResponse;
import net.springboot.product.payload.product.ProductDto;
import net.springboot.product.payload.product.SaveProductRequest;
import net.springboot.security.model.LoggedInUser;
import net.springboot.vendor.model.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class ProductDaoImpl implements ProductDao{

    @PersistenceContext
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImpl.class);

    private final BaseRepository repository;

    public ProductDaoImpl(BaseRepository repository){
        this.repository = repository;
    }

    @Autowired
    MinioService minioService;
    @Value("${minio.bucket.name}")
    public String defaultBucketName;

    @Override
    @Transactional
    public ServiceResponse SaveProduct(SaveProductRequest request){
        try{

            boolean isUpdate = false;
            Product product = new Product();

            if (Utils.isOk(request.getId())) {
                String sql = "SELECT * FROM product WHERE id=:Id ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("Id", request.getId());
                product = repository.findSingleResultByNativeQuery(sql, Product.class, params);

                if (product != null) {
                    isUpdate = true;
                    product.setId(request.getId());
                } else {
                    product = new Product();
                    product.setId(UUID.randomUUID().toString().replace("-","").substring(0,10));
                }
            }
            else{
                product = new Product();
                product.setId(UUID.randomUUID().toString().replace("-","").substring(0,10));
            }

            if (request.getVendorId() > 0) {
                String sql = "SELECT * FROM vendor WHERE id=:vendorId ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("vendorId", request.getVendorId());
                Vendor vendor = repository.findSingleResultByNativeQuery(sql, Vendor.class, params);

                if (vendor == null) {
                    return new ServiceResponse(false, "Wrong vendor id");
                } else {
                    product.setVendorId(vendor);
                }
            }
            else{
                return new ServiceResponse(false, "Vendor is required");
            }

            if (request.getType() > 0){
                product.setType(request.getType());
            }
            else{
                return new ServiceResponse(false, "Wrong Product Type provided");
            }

            if (request.getDetails() != null) {
                product.setDetails(request.getDetails());
            }
            else{
                return new ServiceResponse(false,"Product details is required");
            }

            product.setDescription(request.getDescription());
            product.setAmount(request.getAmount());
            product.setName(request.getName());
            product.setPurchaseCost(request.getPurchaseCost());
            product.setUnitPrice(request.getUnitPrice());
            product.setStatus(request.getStatus().getCode());
            product.setExpiryDate(request.getExpiryDate());
            product.setManufactureDate(request.getManufactureDate());
            product.setPurchaseDate(request.getPurchaseDate());
            product.setDiscountCode(request.getDiscountCode());

            Timestamp timestamp = Utils.getCurrentTimeStamp();

            LoggedInUser user = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (Utils.isOk(request.getPhoto())){
                String name = "product/"+request.getType()+"/"+product.getId()+".jpg";
                minioService.uploadFile(defaultBucketName, name, request.getPhoto(), "jpg");
                product.setPhoto(name);
            }

            if(!isUpdate) {
                product.setEntryDate(timestamp);
                product.setEntryById(user.getId());

                repository.persist(product);
            }
            else if(isUpdate) {
                product.setUpdDate(timestamp);
                product.setUpdById(user.getId());

                repository.merge(product);
            }

        }catch (Throwable t) {
            LOGGER.error("PRODUCT SAVE ERROR:", t);
            return new ServiceResponse(false, "Internal server error. Please contact with admin");
        }
        return new ServiceResponse(true, "PRODUCT has been saved successfully");
    }

    @Override
    @Transactional
    public GetProductResponse GetProduct(GetProductRequest request){
        SearchUtil util = new SearchUtil();

        try{

            GetProductResponse response = new GetProductResponse();

            String sql = "SELECT * FROM product WHERE 1=1 ";
            String sqlCount = "SELECT COUNT(*) FROM product WHERE 1=1 ";
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
            if (Utils.isOk(request.getType())){
                whereClause += " AND type = '"+request.getType()+"'";
            }

            if (Utils.isOk(request.getDiscountCode())){
                whereClause += " AND discount_code = '"+request.getDiscountCode()+"'";
            }

//            if (Utils.isOk(request.getMinPrice())){
//                whereClause += " AND unitPrice >= "+request.getMinPrice();
//            }
//            if (Utils.isOk(request.getMaxPrice())){
//                whereClause += " AND unitPrice <="+request.getMaxPrice();
//            }

//            if (Utils.isOk(request.getVendorId())){
//                whereClause += " AND vendorId = '"+request.getVendorId()+"'";
//            }

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

            qCount = util.setQueryParameter(qCount, request);
            BigInteger count = (BigInteger) qCount.getSingleResult();

            if (count.longValueExact() <= 0 || (count.longValueExact() < (request.getPage()*request.getSize()))){
                return new GetProductResponse("Could not find product");
            }

            Query q = em.createNativeQuery(sql, Product.class);
            q.setFirstResult(request.getPage()*request.getSize());
            q.setMaxResults(request.getSize());
            q = util.setQueryParameter(q,request);

            List<Product> entitylist = q.getResultList();

            if (entitylist != null) {

                List<ProductDto> list = new ArrayList<>();
                entitylist.forEach(product -> {
                    ProductDto obj = new ProductDto();
                    obj.setId(product.getId());
                    obj.setName(product.getName());
                    obj.setStatus(product.getStatus());
                    obj.setAmount(product.getAmount());
                    obj.setDescription(product.getDescription());
                    obj.setDetails(product.getDetails());
                    obj.setDiscountCode(product.getDiscountCode());
                    obj.setExpiryDate(Utils.getDateToString(product.getExpiryDate()));
                    obj.setPurchaseCost(product.getPurchaseCost());
                    obj.setPurchaseDate(Utils.getDateToString(product.getPurchaseDate()));
                    obj.setType(product.getType());
                    obj.setUnitPrice(product.getUnitPrice());
                    obj.setManufactureDate(Utils.getDateToString(product.getManufactureDate()));
                    obj.setVendorName(product.getVendorId().getName());
                    obj.setPhoto(product.getPhoto());

                    list.add(obj);
                });
                response.setProductList(list);
                response.setTotal(count);

                return response;
            }

        }catch (Throwable t) {
            LOGGER.error("Product fetch ERROR:", t);
            return new GetProductResponse("Internal server error. Please contact with admin");
        }

        return new GetProductResponse();
    }
}
